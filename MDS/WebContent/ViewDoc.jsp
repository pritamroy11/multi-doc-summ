<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="dao.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Database</title>
</head>
<body>
<%  DocInfoAccess dao = new DocInfoAccess();
SearchDocAccess kdao = new SearchDocAccess();
dao.openDBConnection();
kdao.openDBConnection();

int k = dao.countDocs();
int[] tl = new int[k];
int i=0;

tl = dao.getDocNo();
%>
<h2><font color="red"><b><u>Database Information : </u></b></font></h2>
<table align="center" >
<tr> 
	<th><u> Document No.</u></th>
	 <th><u> Document Title </u></th>
	 <th><u> Keywords </u></th>
</tr>  
<% 
while(i<k){
%>
<tr> 
<td><font color="maroon" size="+1"><B> <%= tl[i] %></B> </font></td>
<td><font color="maroon" size="+1"><B> <%= dao.getTitle(tl[i]) %></B> </font></td>
<td><font color="maroon" size="+1"><B> <%= kdao.getKeywords(tl[i]) %></B> </font></td>
	<td>
	 <form action="ViewDocSrv" method="post">
	 <input name="docno" type ="hidden" value="<%= tl[i] %>"></input>
	
	 <input type="submit" name="getdoc" value="View Document"> </input>
	
	 </form>
	</td>
	   <!--   
	<td>
	 <form action="UpdateKeywords" method="post">
	 <input name="docno" type ="hidden" value="<%= tl[i] %>"></input>
	
	 <input type="submit" name="updtkey" value="Update Keywords"> </input>
	
	 </form>
	</td>  -->
</tr>
<% i++; } %>

</table>
<% dao.closeDBConnection(); %>

</body>
</html>