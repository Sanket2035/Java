3<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // 1. Fetch parameters from the registration form
    String name = request.getParameter("sname");
    String techDomain = request.getParameter("scourse");
    String email = request.getParameter("semail");
    String pass = request.getParameter("spass");
    String cpass = request.getParameter("scpass");

    // 2. Backend Validation: Ensure passwords match before hitting the database
    if (pass == null || !pass.equals(cpass)) {
        response.sendRedirect("index.html?error=password_mismatch");
        return; // Stop execution
    }

    // 3. Unified MySQL Database Connection Details
    String dbUrl = "jdbc:mysql://localhost:3306/lms";
    String dbUser = "root";
    String dbPass = "Sanket05"; // Ensure this matches your local setup

    Connection con = null;
    PreparedStatement ps = null;

    try {
        // Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // 4. Insert into the new 'students' table
        String sql = "INSERT INTO students (name, tech_domain, email, password_hash) VALUES (?, ?, ?, ?)";
        ps = con.prepareStatement(sql);
        
        ps.setString(1, name);
        ps.setString(2, techDomain);
        ps.setString(3, email);
        ps.setString(4, pass); // Note: In a production app, hash this using BCrypt before saving!

        int rowsInserted = ps.executeUpdate();

        if (rowsInserted > 0) {
            // Success: Redirect back to the login page with a success flag
            response.sendRedirect("index.html?msg=registered");
        } else {
            // Failure: Something went wrong with the insert
            response.sendRedirect("index.html?error=registration_failed");
        }

    } catch (SQLIntegrityConstraintViolationException e) {
        // Specifically catch duplicate emails
        response.sendRedirect("index.html?error=email_exists");
    } catch (Exception e) {
        // Display generic error if database connection fails
        out.println("<div style='color: red; padding: 20px; font-family: sans-serif;'>System Error: " + e.getMessage() + "</div>");
        e.printStackTrace();
    } finally {
        // 5. Safely close resources to prevent connection leaks
        if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
        if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
    }
%>