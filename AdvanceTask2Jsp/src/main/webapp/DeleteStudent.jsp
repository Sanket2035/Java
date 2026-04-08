<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
    // Security Guard: Ensure only logged-in Admins can perform deletions
    if (session.getAttribute("adminName") == null) {
        response.sendRedirect("index.html");
        return; // Stop execution if not authorized
    }

    // 1. Get the Student ID from the URL
    String studentIdStr = request.getParameter("id");

    if (studentIdStr != null && !studentIdStr.trim().isEmpty()) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // 2. Unified MySQL Database Connection Details
            String dbUrl = "jdbc:mysql://localhost:3306/lms";
            String dbUser = "root";
            String dbPass = "Sanket05";

            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            // 3. Delete the student using the new schema
            String sql = "DELETE FROM students WHERE student_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(studentIdStr));

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                // 4. Success! Redirect back to Admin Dashboard with a success flag
                response.sendRedirect("admin_dashboard.jsp?msg=student_deleted");
            } else {
                // In case the ID didn't exist
                response.sendRedirect("admin_dashboard.jsp?msg=error");
            }

        } catch (Exception e) {
            // Display error if something fails
            out.println("<div style='color: red; font-family: sans-serif;'>Database Error: " + e.getMessage() + "</div>");
            e.printStackTrace();
        } finally {
            // 5. Safely close resources
            if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
            if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
        }
    } else {
        // If no ID was provided in the URL, silently return to the dashboard
        response.sendRedirect("admin_dashboard.jsp");
    }
%>