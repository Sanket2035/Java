<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // Fetching parameters from your login form
    String email = request.getParameter("semail"); 
    String pass = request.getParameter("spass");

    // Unified MySQL Database Connection Details
    String dbUrl = "jdbc:mysql://localhost:3306/lms";
    String dbUser = "root";
    String dbPass = "Sanket05";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // Query the new 'admins' table
        String sql = "SELECT * FROM admins WHERE email=? AND password_hash=?";
        ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, pass); // Note: In production, ensure 'pass' is hashed before comparing!

        rs = ps.executeQuery();

        if (rs.next()) {
            // Success: Set Admin Session using new schema column names
            session.setAttribute("adminName", rs.getString("name"));
            session.setAttribute("adminEmail", rs.getString("email"));
            session.setAttribute("role", "admin");
            
            // Redirect to the Admin Dashboard (Ensure this matches your actual filename)
            response.sendRedirect("admin_dashboard.jsp"); 
        } else {
            // Failure: Back to login with an error flag
            response.sendRedirect("index.html?error=invalid");
        }

    } catch (Exception e) {
        // Display error if database connection fails
        out.println("<div class='alert alert-danger'>Database Error: " + e.getMessage() + "</div>");
        e.printStackTrace();
    } finally {
        // Safely close resources to prevent database locking/memory leaks
        if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignored */ }
        if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
        if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
    }
%>