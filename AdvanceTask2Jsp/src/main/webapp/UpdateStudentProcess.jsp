<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
    // Security Guard: Ensure only logged-in Admins can perform updates
    if (session.getAttribute("adminName") == null) {
        response.sendRedirect("index.html");
        return; // Stop execution
    }

    // 1. Fetch updated parameters from the EditStudent form
    String studentIdStr = request.getParameter("student_id");
    String name = request.getParameter("name");
    String techDomain = request.getParameter("tech_domain");
    String email = request.getParameter("email");

    // Validate that we actually received an ID
    if (studentIdStr != null && !studentIdStr.trim().isEmpty()) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // 2. Unified MySQL Database Connection Details
            String dbUrl = "jdbc:mysql://localhost:3306/lms";
            String dbUser = "root";
            String dbPass = "Sanket05"; // Ensure this matches your local setup

            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            // 3. Update the student using the new schema
            String sql = "UPDATE students SET name=?, tech_domain=?, email=? WHERE student_id=?";
            ps = con.prepareStatement(sql);
            
            ps.setString(1, name);
            ps.setString(2, techDomain);
            ps.setString(3, email);
            ps.setInt(4, Integer.parseInt(studentIdStr));

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                // 4. Success! Redirect back to Admin Dashboard with a success flag
                response.sendRedirect("admin_dashboard.jsp?msg=student_updated");
            } else {
                // Failure to update
                response.sendRedirect("admin_dashboard.jsp?msg=error");
            }

        } catch (Exception e) {
            // Display error if something fails
            out.println("<div style='color: red; padding: 20px; font-family: sans-serif;'>Database Error: " + e.getMessage() + "</div>");
            e.printStackTrace();
        } finally {
            // 5. Safely close resources to prevent database connection leaks
            if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
            if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
        }
    } else {
        // If no ID was provided, silently return to the dashboard
        response.sendRedirect("admin_dashboard.jsp");
    }
%>