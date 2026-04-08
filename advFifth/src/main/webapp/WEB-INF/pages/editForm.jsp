<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Student - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- Header Section -->
        <header class="header">
            <h1>Student Management System</h1>
            <p class="subtitle">Edit Student Information</p>
        </header>

        <!-- Navigation Links -->
        <nav class="navigation">
            <a href="${pageContext.request.contextPath}/student?action=view" class="btn btn-secondary">
                &larr; Back to Students List
            </a>
        </nav>

        <!-- Edit Form Section -->
        <div class="card">
            <h2>Update Student Details</h2>

            <!-- Error Message Display -->
            <c:if test="${not empty message}">
                <div class="alert alert-error">
                    ${message}
                </div>
            </c:if>

            <c:choose>
                <c:when test="${not empty student}">
                    <form action="${pageContext.request.contextPath}/student" method="POST" class="student-form">
                        <!-- Hidden field for student ID -->
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${student.id}">

                        <div class="form-group">
                            <label for="id">Student ID</label>
                            <input type="text" id="id" value="${student.id}" disabled class="form-control-disabled">
                        </div>

                        <div class="form-group">
                            <label for="name">Student Name <span class="required">*</span></label>
                            <input type="text" id="name" name="name" value="${student.name}" placeholder="Enter full name" required>
                        </div>

                        <div class="form-group">
                            <label for="email">Email Address <span class="required">*</span></label>
                            <input type="email" id="email" name="email" value="${student.email}" placeholder="Enter email address" required>
                        </div>

                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" value="${student.password}" placeholder="Enter new password">
                            <small class="form-hint">Leave unchanged if you don't want to update the password</small>
                        </div>

                        <div class="form-group">
                            <label for="address">Address</label>
                            <textarea id="address" name="address" rows="3" placeholder="Enter residential address">${student.address}</textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">Update Student</button>
                            <a href="${pageContext.request.contextPath}/student?action=view" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <p>Student not found or invalid student ID.</p>
                        <a href="${pageContext.request.contextPath}/student?action=view" class="btn btn-primary">
                            View All Students
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Footer Section -->
        <footer class="footer">
            <p>&copy; 2026 Student Management System. Built with Java Servlets & JSP.</p>
        </footer>
    </div>
</body>
</html>
