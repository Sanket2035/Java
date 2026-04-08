<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Management System - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- Header Section -->
        <header class="header">
            <h1>Student Management System</h1>
            <p class="subtitle">Manage student records efficiently</p>
        </header>

        <!-- Success Message Display -->
        <c:if test="${param.message == 'added'}">
            <div class="alert alert-success">
                Student added successfully!
            </div>
        </c:if>
        <c:if test="${param.message == 'updated'}">
            <div class="alert alert-success">
                Student updated successfully!
            </div>
        </c:if>
        <c:if test="${param.message == 'deleted'}">
            <div class="alert alert-success">
                Student deleted successfully!
            </div>
        </c:if>

        <!-- Main Content Area -->
        <div class="content-wrapper">
            <!-- Registration Form Section -->
            <div class="card">
                <h2>Register New Student</h2>
                <form action="${pageContext.request.contextPath}/student" method="POST" class="student-form">
                    <input type="hidden" name="action" value="add">

                    <div class="form-group">
                        <label for="name">Student Name <span class="required">*</span></label>
                        <input type="text" id="name" name="name" placeholder="Enter full name" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email Address <span class="required">*</span></label>
                        <input type="email" id="email" name="email" placeholder="Enter email address" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter password">
                    </div>

                    <div class="form-group">
                        <label for="address">Address</label>
                        <textarea id="address" name="address" rows="3" placeholder="Enter residential address"></textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Register Student</button>
                        <button type="reset" class="btn btn-secondary">Clear Form</button>
                    </div>
                </form>
            </div>

            <!-- Quick Links Section -->
            <div class="card">
                <h2>Quick Actions</h2>
                <div class="action-links">
                    <a href="${pageContext.request.contextPath}/student?action=view" class="btn btn-info">
                        View All Students
                    </a>
                    <a href="${pageContext.request.contextPath}/student?action=add" class="btn btn-success">
                        Add New Student
                    </a>
                </div>
            </div>
        </div>

        <!-- Footer Section -->
        <footer class="footer">
            <p>&copy; 2026 Student Management System. Built with Java Servlets & JSP.</p>
        </footer>
    </div>
</body>
</html>
