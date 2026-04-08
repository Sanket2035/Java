<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 1. Retrieve the selected role from the form or URL
    String role = request.getParameter("role");

    // 2. Safety Check: If no role is provided, kick them back to the homepage
    if (role == null || role.trim().isEmpty()) {
        response.sendRedirect("index.html");
        return; // Stop execution
    }

    // 3. Route to the correct login portal cleanly
    switch (role.toLowerCase()) {
        case "admin":
            pageContext.forward("AdminLogin.jsp");
            break;
        case "trainer":
            pageContext.forward("TrainerLogin.jsp");
            break;
        case "student":
            pageContext.forward("StudentLogin.jsp");
            break;
        default:
            // If someone tries to manipulate the URL (e.g., ?role=hacker), send them away
            response.sendRedirect("index.html?error=invalid_role");
            break;
    }
%>