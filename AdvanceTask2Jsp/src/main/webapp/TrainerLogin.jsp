<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // 1. Fetch parameters from the login form
    String email = request.getParameter("semail");
    String pass = request.getParameter("spass");

    // 2. Unified MySQL Database Connection Details
    String dbUrl = "jdbc:mysql://localhost:3306/lms";
    String dbUser = "root";
    String dbPass = "Sanket05"; // Ensure this matches your local setup

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // 3. Query the new 'trainers' table
        String sql = "SELECT * FROM trainers WHERE email=? AND password_hash=?";
        ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, pass); // Note: In production, ensure passwords are hashed!

        rs = ps.executeQuery();

        if (rs.next()) {
            // 4. Check Admin Approval Status FIRST
            if (!rs.getBoolean("is_approved")) {
                // Trainer exists but hasn't been approved by Admin yet
                response.sendRedirect("index.html?error=pending");
            } else {
                // 5. Success & Approved: Set critical session variables
                session.setAttribute("trainerId", rs.getInt("trainer_id")); // CRITICAL for creating exams
                session.setAttribute("trainerName", rs.getString("name"));
                session.setAttribute("trainerEmail", rs.getString("email"));
                session.setAttribute("techDomain", rs.getString("tech_domain")); // CRITICAL for restricting exam topics
                session.setAttribute("role", "trainer");
                
                // Redirect to the Trainer Dashboard (updated filename convention)
                response.sendRedirect("trainer_dashboard.jsp");
            }
        } else {
            // Failure: Invalid credentials
            response.sendRedirect("index.html?error=invalid");
        }

    } catch (Exception e) {
        // Display error if database connection fails
        out.println("<div style='color: red; padding: 20px; font-family: sans-serif;'>Database Error: " + e.getMessage() + "</div>");
        e.printStackTrace();
    } finally {
        // 6. Safely close resources to prevent connection leaks
        if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignored */ }
        if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
        if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
    }
%>