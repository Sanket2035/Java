<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // Security Guard: Check if Admin is logged in
    if (session.getAttribute("adminName") == null) {
        response.sendRedirect("index.html");
        return; // Ensure execution stops after redirect
    }
    String adminName = (String) session.getAttribute("adminName");

    // Unified Database Credentials
    String dbURL = "jdbc:mysql://localhost:3306/lms";
    String dbUser = "root";
    String dbPass = "Sanket05"; // Ensure this matches your local setup
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Control Center | ExamPortal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    
    <style>
        :root {
            --primary-color: #4f46e5;
            --primary-hover: #4338ca;
            --bg-color: #f8fafc;
            --text-main: #0f172a;
            --text-muted: #64748b;
        }
        body { 
            font-family: 'Plus Jakarta Sans', sans-serif; 
            background-color: var(--bg-color); 
            color: var(--text-main); 
        }
        .navbar-admin { 
            background: rgba(255, 255, 255, 0.9); 
            backdrop-filter: blur(10px);
            border-bottom: 1px solid #e2e8f0; 
            padding: 1rem 2rem; 
            z-index: 1000;
        }
        .nav-tabs { 
            border: none; 
            margin-bottom: 2rem; 
            gap: 15px; 
        }
        .nav-tabs .nav-link { 
            border: none; 
            border-radius: 12px; 
            color: var(--text-muted); 
            font-weight: 600; 
            padding: 12px 28px; 
            transition: all 0.3s ease;
            background: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.02);
        }
        .nav-tabs .nav-link:hover {
            color: var(--primary-color);
            transform: translateY(-2px);
        }
        .nav-tabs .nav-link.active { 
            background: var(--primary-color); 
            color: #fff; 
            box-shadow: 0 10px 15px -3px rgba(79, 70, 229, 0.3); 
        }
        .admin-card { 
            background: #fff; 
            border-radius: 20px; 
            border: 1px solid #f1f5f9; 
            box-shadow: 0 10px 25px -5px rgba(0,0,0,0.05); 
            padding: 2.5rem; 
        }
        .table { margin-bottom: 0; }
        .table thead th { 
            background: transparent; 
            border-bottom: 2px solid #f1f5f9; 
            color: var(--text-muted); 
            font-size: 0.75rem; 
            text-transform: uppercase; 
            letter-spacing: 0.1em; 
            padding-bottom: 1rem;
        }
        .table tbody td {
            vertical-align: middle;
            border-bottom: 1px solid #f8fafc;
            padding: 1.2rem 0.5rem;
        }
        .table-hover tbody tr:hover { background-color: #f8fafc; }
        
        .domain-badge {
            background: #eff6ff;
            color: #2563eb;
            font-weight: 600;
            padding: 0.4em 1em;
        }
        
        .btn-action { 
            width: 36px; height: 36px; 
            display: inline-flex; align-items: center; justify-content: center; 
            border-radius: 10px; transition: 0.2s; text-decoration: none;
        }
        .btn-edit { background: #f1f5f9; color: var(--text-main); }
        .btn-delete { background: #fef2f2; color: #ef4444; }
        .btn-edit:hover { background: #e2e8f0; color: #000; }
        .btn-delete:hover { background: #ef4444; color: #fff; }
        
        .empty-state { text-align: center; padding: 3rem 1rem; color: var(--text-muted); }
        .empty-state i { font-size: 3rem; color: #cbd5e1; margin-bottom: 1rem; }
    </style>
</head>
<body>

<nav class="navbar-admin d-flex justify-content-between align-items-center sticky-top">
    <h4 class="fw-800 mb-0" style="letter-spacing: -0.5px;">
        <i class="bi bi-hexagon-fill text-primary me-2"></i>ExamPortal <span class="text-secondary fw-500">Admin</span>
    </h4>
    <div class="d-flex align-items-center gap-4">
        <span class="fw-600 text-muted"><i class="bi bi-person-circle me-2"></i> <%= adminName %></span>
        <a href="Logout.jsp" class="btn btn-light text-danger btn-sm rounded-pill px-4 fw-600 shadow-sm">Logout</a>
    </div>
</nav>

<div class="container py-5">
    <ul class="nav nav-tabs justify-content-center" id="adminTab" role="tablist">
        <li class="nav-item">
            <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#students">
                <i class="bi bi-people-fill me-2"></i> Manage Students
            </button>
        </li>
        <li class="nav-item">
            <button class="nav-link" data-bs-toggle="tab" data-bs-target="#trainers">
                <i class="bi bi-shield-lock-fill me-2"></i> Trainer Approvals
            </button>
        </li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane fade show active" id="students">
            <div class="admin-card">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h5 class="fw-800 mb-1">Registered Students</h5>
                        <p class="text-muted small mb-0">Manage student accounts and domain access.</p>
                    </div>
                    <button class="btn btn-primary btn-sm rounded-pill px-4 fw-600" onclick="window.print()">
                        <i class="bi bi-printer-fill me-2"></i> Export Report
                    </button>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>Name</th>
                                <th>Tech Domain</th>
                                <th>Email</th>
                                <th class="text-end">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                boolean hasStudents = false;
                                try {
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    Connection con = DriverManager.getConnection(dbURL, dbUser, dbPass);
                                    Statement st = con.createStatement();
                                    // Using new schema column names
                                    ResultSet rs = st.executeQuery("SELECT * FROM students ORDER BY student_id DESC");
                                    
                                    while(rs.next()) {
                                        hasStudents = true;
                            %>
                            <tr>
                                <td class="text-muted fw-500">#STU-<%= rs.getInt("student_id") %></td>
                                <td class="fw-800"><%= rs.getString("name") %></td>
                                <td><span class="badge domain-badge rounded-pill"><i class="bi bi-laptop me-1"></i> <%= rs.getString("tech_domain") %></span></td>
                                <td class="text-muted"><%= rs.getString("email") %></td>
                                <td class="text-end">
                                    <a href="EditStudent.jsp?id=<%= rs.getInt("student_id") %>" class="btn-action btn-edit me-2" title="Edit"><i class="bi bi-pencil-square"></i></a>
                                    <a href="DeleteStudent.jsp?id=<%= rs.getInt("student_id") %>" onclick="return confirm('Are you sure you want to delete this student?')" class="btn-action btn-delete" title="Delete"><i class="bi bi-trash3-fill"></i></a>
                                </td>
                            </tr>
                            <% 
                                    } 
                                    con.close(); 
                                } catch(Exception e) { out.println("<div class='alert alert-danger'>Database Error: " + e.getMessage() + "</div>"); } 
                                
                                if(!hasStudents) {
                            %>
                            <tr>
                                <td colspan="5">
                                    <div class="empty-state">
                                        <i class="bi bi-inbox"></i>
                                        <h6>No students registered yet</h6>
                                        <p class="small">When students sign up, they will appear here.</p>
                                    </div>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="trainers">
            <div class="admin-card">
                <div class="mb-4">
                    <h5 class="fw-800 mb-1">Pending Trainer Approvals</h5>
                    <p class="text-muted small mb-0">Review and approve trainers before they can create exams.</p>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Trainer ID</th>
                                <th>Name</th>
                                <th>Tech Domain</th>
                                <th>Email</th>
                                <th class="text-end">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                boolean hasPendingTrainers = false;
                                try {
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    Connection con = DriverManager.getConnection(dbURL, dbUser, dbPass);
                                    // Querying for unapproved trainers based on new schema
                                    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM trainers WHERE is_approved = FALSE ORDER BY trainer_id ASC");
                                    
                                    while(rs.next()) {
                                        hasPendingTrainers = true;
                            %>
                            <tr>
                                <td class="text-muted fw-500">#TRN-<%= rs.getInt("trainer_id") %></td>
                                <td class="fw-800"><%= rs.getString("name") %></td>
                                <td><span class="badge domain-badge rounded-pill"><i class="bi bi-journal-code me-1"></i> <%= rs.getString("tech_domain") %></span></td>
                                <td class="text-muted"><%= rs.getString("email") %></td>
                                <td class="text-end">
                                    <a href="ApproveTrainer.jsp?id=<%= rs.getInt("trainer_id") %>" class="btn btn-success btn-sm rounded-pill px-4 fw-600 shadow-sm">
                                        <i class="bi bi-check-circle-fill me-1"></i> Approve
                                    </a>
                                </td>
                            </tr>
                            <% 
                                    } 
                                    con.close(); 
                                } catch(Exception e) { out.println("<div class='alert alert-danger'>Database Error: " + e.getMessage() + "</div>"); } 
                                
                                if(!hasPendingTrainers) {
                            %>
                            <tr>
                                <td colspan="5">
                                    <div class="empty-state">
                                        <i class="bi bi-check2-circle text-success"></i>
                                        <h6>All Caught Up!</h6>
                                        <p class="small">There are no pending trainer applications requiring approval.</p>
                                    </div>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>