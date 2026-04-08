<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/style.css" type="text/css">
<script src="${pageContext.request.contextPath}/resources/JS/script.js" type="text/javascript"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<h1 style="color: orange;">Hello Chaitu!</h1>
	<h2>Welcome to Spring Boot</h2>
	
	<button type="button" onclick="test()">Click Here</button>
</body>
</html>