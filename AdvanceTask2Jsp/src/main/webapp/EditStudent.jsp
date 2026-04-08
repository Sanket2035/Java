<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // Security Guard: Stop unauthorized access
    if (session.getAttribute("adminName") == null) {
        response.sendRedirect("index.html");
        return; 
    }

    String studentIdStr = request.getParameter("id");
    String studentName = "", techDomain = "", studentEmail = "";

    // 1. Fetch Current Data using Unified MySQL Connection
    if (studentIdStr != null && !studentIdStr.trim().isEmpty()) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String dbUrl = "jdbc:mysql://localhost:3306/lms";
            String dbUser = "root";
            String dbPass = "Sanket05";

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            
            // Query the new students table
            ps = con.prepareStatement("SELECT * FROM students WHERE student_id = ?");
            ps.setInt(1, Integer.parseInt(studentIdStr));
            rs = ps.executeQuery();
            
            if(rs.next()){
                studentName = rs.getString("name");
                techDomain = rs.getString("tech_domain");
                studentEmail = rs.getString("email");
            }
        } catch(Exception e) { 
            e.printStackTrace(); 
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException e) {}
            if (ps != null) try { ps.close(); } catch(SQLException e) {}
            if (con != null) try { con.close(); } catch(SQLException e) {}
        }
    } else {
        response.sendRedirect("admin_dashboard.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Student | Admin Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;800&display=swap" rel="stylesheet">
    <style>
        body { 
            font-family: 'Plus Jakarta Sans', sans-serif; 
            background: #f8fafc; 
            display: flex; 
            align-items: center; 
            justify-content: center; 
            min-height: 100vh; 
        }
        .edit-card { 
            background: #fff; 
            padding: 40px; 
            border-radius: 20px; 
            border: 1px solid #f1f5f9;
            box-shadow: 0 10px 25px -5px rgba(0,0,0,0.05); 
            width: 100%; 
            max-width: 500px; 
        }
        .form-control, .form-select { 
            border-radius: 12px; 
            border: 2px solid #f1f5f9; 
            padding: 12px 16px; 
            font-weight: 500;
            color: #0f172a;
        }
        .form-control:focus, .form-select:focus {
            border-color: #4f46e5;
            box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
        }
        .form-label { color: #64748b; font-size: 0.9rem; }
        .btn-update { 
            background: #4f46e5; 
            color: #fff; 
            font-weight: 700; 
            border-radius: 12px; 
            width: 100%; 
            border: none; 
            padding: 14px; 
            transition: all 0.3s ease; 
        }
        .btn-update:hover { 
            background: #4338ca; 
            transform: translateY(-2px); 
            box-shadow: 0 10px 15px -3px rgba(79, 70, 229, 0.3);
        }
    </style>
</head>
<body>

<div class="edit-card">
    <div class="mb-4 text-center">
        <h3 class="fw-800 text-primary mb-1">Edit Student</h3>
        <p class="text-muted small">Update records for #STU-<%= studentIdStr %></p>
    </div>
    
    <form action="UpdateStudentProcess.jsp" method="post">
        <input type="hidden" name="student_id" value="<%= studentIdStr %>">

        <div class="mb-3">
            <label class="form-label fw-600">Full Name</label>
            <input type="text" name="name" class="form-control" value="<%= studentName %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label fw-600">Tech Domain</label>
            <select name="tech_domain" class="form-select">
                <option value="Full-Stack Java" <%= techDomain.equals("Full-Stack Java") ? "selected" : "" %>>Full-Stack Java</option>
                <option value="Docker" <%= techDomain.equals("Docker") ? "selected" : "" %>>Docker</option>
                <option value="Kubernetes" <%= techDomain.equals("Kubernetes") ? "selected" : "" %>>Kubernetes</option>
            </select>
        </div>

        <div class="mb-4">
            <label class="form-label fw-600">Email Address</label>
            <input type="email" name="email" class="form-control" value="<%= studentEmail %>" required>
        </div>

        <button type="submit" class="btn btn-update">Save Changes</button>
        <div class="text-center mt-4">
            <a href="admin_dashboard.jsp" class="text-muted text-decoration-none small fw-600 hover-primary">← Cancel and Go Back</a>
        </div>
    </form>
</div>

</body>
</html>