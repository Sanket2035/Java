<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- Header Section -->
        <header class="header">
            <h1>Student Management System</h1>
            <p class="subtitle">Error</p>
        </header>

        <!-- Error Card -->
        <div class="card">
            <div class="error-icon">&#9888;</div>
            <h2>Oops! Something went wrong</h2>

            <c:choose>
                <c:when test="${not empty message}">
                    <p class="error-message">${message}</p>
                </c:when>
                <c:when test="${not empty exception}">
                    <p class="error-message">Error: ${exception}</p>
                </c:when>
                <c:otherwise>
                    <p class="error-message">An unexpected error occurred. Please try again.</p>
                </c:otherwise>
            </c:choose>

            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/index.jsp" class="btn btn-primary">
                    Go to Home
                </a>
                <a href="${pageContext.request.contextPath}/student?action=view" class="btn btn-secondary">
                    View All Students
                </a>
            </div>
        </div>

        <!-- Footer Section -->
        <footer class="footer">
            <p>&copy; 2026 Student Management System. Built with Java Servlets & JSP.</p>
        </footer>
    </div>
</body>
</html>
