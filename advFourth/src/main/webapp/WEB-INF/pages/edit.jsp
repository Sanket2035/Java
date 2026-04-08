<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile Page</title>
</head>
<body>
	<form action="../update" method="post">
			<input type="number" name="id" value=${s.id}><br><br>
			<input type="text" name="name" value=${s.name}><br><br>
			<input type="email" name="email" value=${s.email}><br><br>
			<input type="password" name="password" value=${s.password}><br><br>
			<input type="text" name="address" value=${s.address}><br><br>
			
			<button type="submit">Submit</button>
		</form>
</body>
</html>