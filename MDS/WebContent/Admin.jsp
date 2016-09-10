<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrator Login</title>
</head>
<body>
	<center><form name="loginform" action="LoginValidation.jsp">
		<table>
			<tr>
				<td><h2><font color="purple"><u>...Welcome Administrator...</u></font></h2></td>
			</tr>
			<table>
			<tr>
				<td><b>User Id : </b></td>
				<td><input type="text" name="uid"></input></td>
			</tr>
			<tr>
				<td><b>Password : </b></td>
				<td><input type="password" name="pass"></input></td>
			</tr>
			</table><br>
			<tr>
				<td><input type="submit" name="login" value="Login"></input></td>
			</tr>
		</table>
	</form>
	</center>
</body>
</html>