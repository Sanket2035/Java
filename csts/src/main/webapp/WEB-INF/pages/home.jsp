<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login Redirector</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    
  </head>
  <body>
  	<%@ include file="header.jsp" %>
    <div class="container">
    	<div class="row">
    		<div class="col-4"></div>
    		<div class="col-4">
    			<div class="card">
				  <div class="card-header">
				    Login Redirector
				  </div>
				  <div class="card-body">
				  	<br>
				    <a href="adminLogin" class="btn btn-primary">Admin Login</a><br><br>
				    <a href="staffLogin" class="btn btn-secondary">Staff Login</a><br><br>
				    <a href="customerRegister" class="btn btn-success">Customer Registration</a><br><br>
				    <a href="customerLogin" class="btn btn-success">Customer Login</a><br><br>
				  </div>
				</div>
    		</div>
    		<div class="col-4"></div>
    	</div>
    </div>
  </body>
</html>