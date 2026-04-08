<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View All Students - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- Header Section -->
        <header class="header">
            <h1>Student Management System</h1>
            <p class="subtitle">View All Registered Students</p>
        </header>

        <!-- Navigation Links -->
        <nav class="navigation">
            <a href="${pageContext.request.contextPath}/WEB-INF/pages/index.jsp" class="btn btn-secondary">
                &larr; Back to Home
            </a>
            <a href="${pageContext.request.contextPath}/WEB-INF/pages/index.jsp?action=add" class="btn btn-primary">
                + Add New Student
            </a>
        </nav>

        <!-- Students Table Section -->
        <div class="card">
            <h2>
                Registered Students
                <c:if test="${not empty count}">
                    <span class="badge">${count} student(s)</span>
                </c:if>
            </h2>

            <c:choose>
                <c:when test="${not empty studentList && count > 0}">
                    <div class="table-responsive">
                        <table class="students-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Address</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="student" items="${studentList}">
                                    <tr>
                                        <td>${student.id}</td>
                                        <td>${student.name}</td>
                                        <td>${student.email}</td>
                                        <td>${student.address}</td>
                                        <td>
                                            <div class="action-buttons">
                                                <!-- Edit Button -->
                                                <a href="${pageContext.request.contextPath}/student?action=edit&id=${student.id}"
                                                   class="btn btn-sm btn-warning"
                                                   onclick="return confirm('Are you sure you want to edit this student?')">
                                                    Edit
                                                </a>

                                                <!-- Delete Button -->
                                                <a href="${pageContext.request.contextPath}/student?action=delete&id=${student.id}"
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Are you sure you want to delete this student? This action cannot be undone.')">
                                                    Delete
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <p>No students registered yet.</p>
                        <a href="${pageContext.request.contextPath}/WEB-INF/pages/index.jsp" class="btn btn-primary">
                            Register First Student
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
