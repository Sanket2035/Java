<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Students Profile Info Page</title>
</head>
<body>
	<h1>Student Information Page!</h1>
	
	<table border="1">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Email</th>
			<th>Password</th>
			<th>Address</th>
			<th>Action</th>
		</tr>
		<c:forEach var="s" items="${list}" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${s.name}</td>
				<td>${s.email}</td>
				<td>${s.password}</td>
				<td>${s.address}</td>
				<td>
					<a href="deletestudent/${s.id}">Delete</a>
					<a href="editprofile/${s.id}">Edit</a>
				</td>
			</tr>		
		</c:forEach>
	</table>
</body>
</html>