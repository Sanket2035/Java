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

        // 3. Query the new 'students' table
        String sql = "SELECT * FROM students WHERE email=? AND password_hash=?";
        ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, pass); // Note: In production, ensure passwords are hashed!

        rs = ps.executeQuery();

        if (rs.next()) {
            // 4. Success: Set critical session variables
            session.setAttribute("studentId", rs.getInt("student_id")); // CRITICAL for the dashboard
            session.setAttribute("studentName", rs.getString("name"));
            session.setAttribute("studentEmail", rs.getString("email"));
            session.setAttribute("techDomain", rs.getString("tech_domain"));
            session.setAttribute("role", "student"); // Good practice for global routing
            
            // Redirect to the dynamic dashboard we just built
            response.sendRedirect("student_dashboard.jsp");
        } else {
            // Failure: Back to login with error flag
            response.sendRedirect("index.html?error=invalid");
        }

    } catch (Exception e) {
        // Display error if database connection fails
        out.println("<div style='color: red; padding: 20px; font-family: sans-serif;'>Database Error: " + e.getMessage() + "</div>");
        e.printStackTrace();
    } finally {
        // 5. Safely close resources to prevent connection leaks
        if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignored */ }
        if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
        if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
    }
%>