<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
    // Security Guard: Ensure only logged-in Admins can trigger this action
    if (session.getAttribute("adminName") == null) {
        response.sendRedirect("index.html");
        return; // Stop execution if not authorized
    }

    // 1. Get the Trainer ID from the URL (e.g., ApproveTrainer.jsp?id=5)
    String trainerIdStr = request.getParameter("id");

    if (trainerIdStr != null && !trainerIdStr.trim().isEmpty()) {
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

            // 3. Update the Trainer's status using the new schema
            String sql = "UPDATE trainers SET is_approved = TRUE WHERE trainer_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(trainerIdStr));

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                // 4. Success! Redirect back to Admin Dashboard (updated filename)
                // Note: You can add logic in admin_dashboard.jsp to show a toast/alert for 'msg=approved'
                response.sendRedirect("admin_dashboard.jsp?msg=approved");
            } else {
                response.sendRedirect("admin_dashboard.jsp?msg=error");
            }

        } catch (Exception e) {
            // Display error if something fails
            out.println("<div style='color: red;'>Database Error: " + e.getMessage() + "</div>");
            e.printStackTrace();
        } finally {
            // 5. Safely close resources to prevent database connection leaks
            if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignored */ }
            if (con != null) try { con.close(); } catch(SQLException e) { /* ignored */ }
        }
    } else {
        // If no ID was provided in the URL, silently return to the dashboard
        response.sendRedirect("admin_dashboard.jsp");
    }
%>