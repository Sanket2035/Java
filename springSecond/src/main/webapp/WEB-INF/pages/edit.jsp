<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register Page</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
 
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4">
				<h1 class="align-content-center">Edit Details</h1>
			<form action="/update" method="post">
			  <div class="mb-3">
			    <input type="hidden" class="form-control" name="id" value="${s.id}">
			  </div>
			  <div class="mb-3">
			    <input type="text" class="form-control" name="name" value="${s.name}">
			  </div>
			  <div class="mb-3">
			    <input type="email" class="form-control" name="email" value="${s.email}">
			  </div>
			  <div class="mb-3">
			    <input type="password" class="form-control" name="password" value="${s.password}">
			  </div>
			  <div class="mb-3">
			    <input type="text" class="form-control" name="address" value="${s.address}">
			  </div>
			  <button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</div>
		<div class="col-sm-4"></div>
	</div>
</body>
</html>