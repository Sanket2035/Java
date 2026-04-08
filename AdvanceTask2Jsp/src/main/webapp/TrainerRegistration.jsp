<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // 1. Fetch parameters from the registration form
    // Note: Kept your original HTML input names (sname, scourse, etc.)
    String name = request.getParameter("sname");
    String techDomain = request.getParameter("scourse"); 
    String email = request.getParameter("semail");
    String pass = request.getParameter("spass");

    // 2. Unified MySQL Database Connection Details
    String dbUrl = "jdbc:mysql://localhost:3306/lms";
    String dbUser = "root";
    String dbPass = "Sanket05"; // Ensure this matches your local setup

    Connection con = null;
    PreparedStatement ps = null;

    try {
        // Load MySQL Driver (Fixed the Postgres driver bug)
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // 3. Insert into the new 'trainers' table
        // Note: is_approved automatically defaults to FALSE in our schema, so we don't need to specify it
        String sql = "INSERT INTO trainers (name, tech_domain, email, password_hash) VALUES (?, ?, ?, ?)";
        ps = con.prepareStatement(sql);
        
        ps.setString(1, name);
        ps.setString(2, techDomain);
        ps.setString(3, email);
        ps.setString(4, pass); // In a real production app, ensure this is hashed!

        int rowsInserted = ps.executeUpdate();

        if (rowsInserted > 0) {
            // Success: Redirect back to login with a pending approval flag
            response.sendRedirect("index.html?msg=trainer_pending");
        } else {
            // Failure: Something went wrong with the insert
            response.sendRedirect("index.html?error=registration_failed");
        }

    } catch (SQLIntegrityConstraintViolationException e) {
        // Specifically catch duplicate emails
        response.sendRedirect("index.html?error=email_exists");
    } catch (Exception e) {
        // Redirect with a generic error flag to trigger a failure alert on the frontend
        response.sendRedirect("index.html?error=system_error");
        e.printStackTrace();
    } finally {
        // 4. Safely close resources to prevent connection leaks
        if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
        if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
    }
%>