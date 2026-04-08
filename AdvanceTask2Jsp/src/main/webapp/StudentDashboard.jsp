<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
    // Security Guard: Redirect to login if session is empty
    if (session.getAttribute("studentName") == null || session.getAttribute("studentId") == null) {
        response.sendRedirect("index.html");
        return; // Stop execution
    }
    
    String name = (String) session.getAttribute("studentName");
    String techDomain = (String) session.getAttribute("techDomain"); // Changed from course to techDomain
    int studentId = (Integer) session.getAttribute("studentId");

    // Initialize statistics variables
    int completedExams = 0;
    int totalDomainExams = 0;
    int pendingExams = 0;
    int avgScore = 0;

    // Database Connection Variables
    String dbUrl = "jdbc:mysql://localhost:3306/lms";
    String dbUser = "root";
    String dbPass = "Sanket05";
    
    Connection con = null;
    PreparedStatement psStats = null;
    PreparedStatement psExams = null;
    ResultSet rsStats = null;
    ResultSet rsExams = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // 1. Calculate Dashboard Statistics
        // Get total completed and average score
        String statsQuery = "SELECT COUNT(exam_id) as total_completed, ROUND(AVG(score)) as avg_score FROM results WHERE student_id = ?";
        psStats = con.prepareStatement(statsQuery);
        psStats.setInt(1, studentId);
        rsStats = psStats.executeQuery();
        
        if(rsStats.next()) {
            completedExams = rsStats.getInt("total_completed");
            avgScore = rsStats.getInt("avg_score"); // Will be 0 if no exams taken
        }
        
        // Get total available exams for their domain
        PreparedStatement psTotal = con.prepareStatement("SELECT COUNT(exam_id) as total_exams FROM exams WHERE tech_domain = ?");
        psTotal.setString(1, techDomain);
        ResultSet rsTotal = psTotal.executeQuery();
        
        if(rsTotal.next()) {
            totalDomainExams = rsTotal.getInt("total_exams");
        }
        rsTotal.close();
        psTotal.close();

        // Calculate pending
        pendingExams = totalDomainExams - completedExams;
        if (pendingExams < 0) pendingExams = 0;

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | <%= techDomain %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    
    <style>
        :root { --primary: #4f46e5; --primary-hover: #4338ca; }
        body { font-family: 'Plus Jakarta Sans', sans-serif; background-color: #f8fafc; color: #0f172a; }
        .sidebar { height: 100vh; background: #fff; border-right: 1px solid #e2e8f0; padding: 24px 20px; position: fixed; width: 280px; z-index: 100; }
        .main-content { margin-left: 280px; padding: 40px; }
        .nav-link { color: #64748b; font-weight: 600; padding: 12px 18px; border-radius: 12px; margin-bottom: 5px; transition: all 0.2s; }
        .nav-link.active { background: var(--primary); color: #fff; box-shadow: 0 4px 12px rgba(79, 70, 229, 0.2); }
        .nav-link:hover:not(.active) { background: #f1f5f9; color: var(--primary); transform: translateX(4px); }
        
        .stat-card { background: #fff; border-radius: 24px; padding: 28px; border: 1px solid #f1f5f9; box-shadow: 0 10px 25px -5px rgba(0,0,0,0.05); transition: 0.3s; }
        .stat-card:hover { transform: translateY(-3px); box-shadow: 0 20px 25px -5px rgba(0,0,0,0.05); }
        
        .exam-card { background: #fff; border-radius: 24px; border: 1px solid #e2e8f0; transition: 0.3s; padding: 28px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.02); }
        .exam-card:hover { transform: translateY(-5px); border-color: #c7d2fe; box-shadow: 0 20px 25px -5px rgba(79, 70, 229, 0.1); }
        .badge-course { background: #e0e7ff; color: var(--primary); font-weight: 700; padding: 0.5em 1em; }
        
        .btn-start { background: var(--primary); color: white; transition: 0.3s; }
        .btn-start:hover { background: var(--primary-hover); transform: scale(1.02); }
    </style>
</head>
<body>

    <div class="sidebar d-none d-lg-block">
        <div class="mb-5 px-3">
            <h4 class="fw-800 text-primary mb-0" style="letter-spacing: -0.5px;"><i class="bi bi-hexagon-fill me-2"></i>ExamPortal</h4>
        </div>
        <nav class="nav flex-column">
            <a class="nav-link active" href="student_dashboard.jsp"><i class="bi bi-grid-1x2-fill me-3"></i> Dashboard</a>
            <a class="nav-link" href="#"><i class="bi bi-journal-text me-3"></i> Available Exams</a>
            <a class="nav-link" href="#"><i class="bi bi-trophy-fill me-3"></i> My Results</a>
            <hr class="my-4 text-muted">
            <a class="nav-link text-danger" href="Logout.jsp"><i class="bi bi-box-arrow-left me-3"></i> Logout</a>
        </nav>
    </div>

    <div class="main-content">
        <header class="d-flex justify-content-between align-items-center mb-5">
            <div>
                <h2 class="fw-800 mb-2">Welcome back, <%= name %>! 👋</h2>
                <p class="text-muted fw-500 mb-0">You are enrolled in <span class="badge badge-course rounded-pill ms-1"><%= techDomain %></span></p>
            </div>
            <div class="dropdown">
                <img src="https://ui-avatars.com/api/?name=<%= name %>&background=4f46e5&color=fff&bold=true" class="rounded-circle shadow-sm" width="48" height="48">
            </div>
        </header>

        <div class="row g-4 mb-5">
            <div class="col-md-4">
                <div class="stat-card">
                    <div class="d-flex align-items-center mb-3">
                        <div class="bg-warning bg-opacity-10 p-2 rounded-3 me-3"><i class="bi bi-hourglass-split text-warning fs-5"></i></div>
                        <div class="text-muted small fw-600 text-uppercase tracking-wide">Pending Exams</div>
                    </div>
                    <h2 class="fw-800 mb-0"><%= String.format("%02d", pendingExams) %></h2>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card">
                    <div class="d-flex align-items-center mb-3">
                        <div class="bg-success bg-opacity-10 p-2 rounded-3 me-3"><i class="bi bi-check-circle-fill text-success fs-5"></i></div>
                        <div class="text-muted small fw-600 text-uppercase tracking-wide">Completed</div>
                    </div>
                    <h2 class="fw-800 mb-0"><%= String.format("%02d", completedExams) %></h2>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card">
                    <div class="d-flex align-items-center mb-3">
                        <div class="bg-primary bg-opacity-10 p-2 rounded-3 me-3"><i class="bi bi-graph-up-arrow text-primary fs-5"></i></div>
                        <div class="text-muted small fw-600 text-uppercase tracking-wide">Avg. Score</div>
                    </div>
                    <h2 class="fw-800 mb-0"><%= avgScore %>%</h2>
                </div>
            </div>
        </div>

        <h4 class="fw-800 mb-4 d-flex align-items-center">
            <i class="bi bi-journal-bookmark-fill text-primary me-2"></i> Your Exam Catalog
        </h4>
        
        <div class="row g-4">
            <%
                // 2. Fetch Exams specific to the student's domain and check if they have taken them
                String examQuery = "SELECT e.exam_id, e.title, r.score FROM exams e " +
                                   "LEFT JOIN results r ON e.exam_id = r.exam_id AND r.student_id = ? " +
                                   "WHERE e.tech_domain = ? ORDER BY e.exam_id DESC";
                
                psExams = con.prepareStatement(examQuery);
                psExams.setInt(1, studentId);
                psExams.setString(2, techDomain);
                rsExams = psExams.executeQuery();

                boolean hasExams = false;
                while(rsExams.next()) {
                    hasExams = true;
                    int examId = rsExams.getInt("exam_id");
                    String title = rsExams.getString("title");
                    
                    // Check if score exists (meaning exam was taken)
                    Object scoreObj = rsExams.getObject("score");
                    boolean isCompleted = (scoreObj != null);
                    int studentScore = isCompleted ? rsExams.getInt("score") : 0;
            %>
            <div class="col-md-6 col-lg-4">
                <div class="exam-card">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <span class="badge bg-soft-primary text-primary px-3 py-2 rounded-pill" style="background: #eef2ff; font-weight: 600;"><%= techDomain %></span>
                        <% if(isCompleted) { %>
                            <span class="badge bg-success rounded-pill px-3 py-2"><i class="bi bi-check2-all me-1"></i> Completed</span>
                        <% } else { %>
                            <span class="badge bg-warning text-dark rounded-pill px-3 py-2"><i class="bi bi-clock me-1"></i> Pending</span>
                        <% } %>
                    </div>
                    
                    <h5 class="fw-800 mb-2"><%= title %></h5>
                    <p class="text-muted small mb-4">Test your knowledge on core concepts for this module.</p>
                    
                    <% if(!isCompleted) { %>
                        <a href="TakeExam.jsp?examId=<%= examId %>" class="btn btn-start w-100 rounded-pill fw-700 py-2">
                            <i class="bi bi-play-circle-fill me-2"></i> Start Exam
                        </a>
                    <% } else { %>
                        <div class="p-3 bg-light rounded-4 text-center border">
                            <span class="text-muted small d-block fw-600 mb-1">Your Score</span>
                            <span class="fs-4 fw-800 text-success"><%= studentScore %>%</span>
                        </div>
                    <% } %>
                </div>
            </div>
            <%
                }
                
                if(!hasExams) {
            %>
                <div class="col-12">
                    <div class="text-center p-5 bg-white rounded-4 border">
                        <i class="bi bi-inbox text-muted" style="font-size: 3rem;"></i>
                        <h5 class="fw-700 mt-3">No Exams Available</h5>
                        <p class="text-muted">Your trainers haven't published any exams for <%= techDomain %> yet. Check back later!</p>
                    </div>
                </div>
            <%  } %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%
    } catch (Exception e) {
        out.println("<div class='alert alert-danger m-5 shadow-sm'><strong>System Error:</strong> " + e.getMessage() + "</div>");
        e.printStackTrace();
    } finally {
        // Safely close resources
        if (rsExams != null) try { rsExams.close(); } catch(SQLException e) {}
        if (rsStats != null) try { rsStats.close(); } catch(SQLException e) {}
        if (psExams != null) try { psExams.close(); } catch(SQLException e) {}
        if (psStats != null) try { psStats.close(); } catch(SQLException e) {}
        if (con != null) try { con.close(); } catch(SQLException e) {}
    }
%>