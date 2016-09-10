<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrator Login</title>
</head>
<body>
<% String uid = request.getParameter("uid");
String pass = request.getParameter("pass");
if(uid.isEmpty() || pass.isEmpty()){ %>
<jsp:include page="Admin.jsp"></jsp:include>
<h3 align="center"> <font color = "red"> User ID and/or Password cannot be empty!!! </font></h3>
<% } else if (uid.equalsIgnoreCase("bijoy") && pass.equalsIgnoreCase("bijoy")) { %>
	
	<h1><font color="green"><u> Welcome Mr. Bijoy Kumar Mandal </u></font></h1>
	<img alt="Bijoy Kumar Mandal" src="./Images/BijoyMandal.jpg" height="150" width="120">
	<a href="AdminLinks.jsp" target="Links" onClick="top.Disp.location='SearchPage.jsp';"><h3><b><centre>Click to Proceed >>>>></b></h3></a>

<% } else if (uid.equalsIgnoreCase("pritam") && pass.equalsIgnoreCase("roy.prit")) { %>
	
	<h1><font color="green"><u> Welcome Mr. Pritam Roy </u></font></h1>
	<img alt="Pritam Roy" src="./Images/PritamRoy.jpg" height="150" width="120">
	<a href="AdminLinks.jsp" target="Links" onClick="top.Disp.location='SearchPage.jsp';"><h3><b><centre>Click to Proceed >>>>></b></h3></a>
	
<% } else if (uid.equalsIgnoreCase("suman") && pass.equalsIgnoreCase("uncivilized")) { %>
	
	<h1><font color="green"><u> Welcome Mr. Suman Saha </u></font></h1>
	<img alt="Suman Saha" src="./SumanSaha.jpg" height="150" width="120">
	<a href="AdminLinks.jsp" target="Links" onClick="top.Disp.location='SearchPage.jsp';"><h3><b><centre>Click to Proceed >>>>></b></h3></a>
	
<% } else if (uid.equalsIgnoreCase("saurav") && pass.equalsIgnoreCase("bombay")) { %>
	
	<h1><font color="green"><u> Welcome Mr. Saurav Das </u></font></h1>
	<img alt="Saurav Das" src="./Images/SauravDas.jpg" height="150" width="120">
	<a href="AdminLinks.jsp" target="Links" onClick="top.Disp.location='SearchPage.jsp';"><h3><b><centre>Click to Proceed >>>>></b></h3></a>
	
<% } else if (uid.equalsIgnoreCase("sourav") && pass.equalsIgnoreCase("jomu")) { %>
	
	<h1><font color="green"><u> Welcome Mr. Sourav Ghosh </u></font></h1>
	<img alt="Sourav Ghosh" src="./Images/SouravGhosh.jpg" height="150" width="120">
	<a href="AdminLinks.jsp" target="Links" onClick="top.Disp.location='SearchPage.jsp';"><h3><b><centre>Click to Proceed >>>>></b></h3></a>
		
<%	} else { %>
<jsp:include page="Admin.jsp"></jsp:include>
<h3 align="center"> <font color = "red"> OOPS!!! You have entered Wrong User ID and/or Password...</font></h3>
<%
}
%>
	
</body>
</html>