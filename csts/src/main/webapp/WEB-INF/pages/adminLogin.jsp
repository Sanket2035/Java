<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
   		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    
	</head>
	<body>
		<%@ include file="header.jsp" %>
		<div class="container">
			<div class="row">
				<div class="col-4"></div>
				<div class="col-4">
					<form action="adminlogin" method="post" class="form-signin">
				      <h1 class="h3 mb-3 font-weight-normal">Please Login</h1>
				      <label for="email" class="sr-only">Email address</label>
				      <input type="email" name="email" class="form-control" placeholder="Email address" required autofocus><br>
				      <label for="password" class="sr-only">Password</label>
				      <input type="password" name="password" class="form-control" placeholder="Password" required><br>
				      <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
				    </form>
				</div>
				<div class="col-4"></div>
			</div>
		</div>
	</body>
</html>