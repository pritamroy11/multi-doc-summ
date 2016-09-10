<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Data Here</title>
</head>
<body>
	<form name="inputform" method="post" action="InputSrv">
		<table>
			<tr>
				<td><h3>Enter the document Title :</h3></td>
				<td><input type="text" name="title" maxlength='100'></input></td>
			</tr>
			<tr>
				<td><h3>Enter Part 1 of document : </h3></td>
				<td><textarea rows=5 cols=50 name="intro""></textarea> </td> <!--  onkeypress="if (this.value.length > 1999) { return false; } -->
			</tr>
			<tr>
				<td><h3>Enter Part 2 of document : </h3></td>
				<td><textarea rows=5 cols=50 name="desc" ></textarea> </td>
			</tr>
			<tr>
				<td><h3>Enter Part 3 of document : </h3></td>
				<td><textarea rows=5 cols=50 name="feat" ></textarea> </td>
			</tr>
			<tr>
				<td><h3>Enter Part 4 of document : </h3></td>
				<td><textarea rows=5 cols=50 name="concl" ></textarea> </td>
			</tr>
			<tr>
				<td><h3>Enter document Keywords (separated by a space) : </h3></td>
				<td><input type="text" name="keys" maxlength='100'></input></td>
			</tr><br>
			<tr>
				<td><h2><input type="submit" value="SUBMIT DOCUMENT"></h2></td>
				<td><h3><input type="reset" value="CLEAR FORM"></h3></td>
			</tr>
		</table>
	</form>
</body>
</html>