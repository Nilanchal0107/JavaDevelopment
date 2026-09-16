<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Web App</title>
</head>
<body>
<h1>JSP Web App to generate Dynamic Response</h1>


<%@
	page import="java.util.Date"
 %>
 
<%!
	int age = 18;
%>

<%
	String name = request.getParameter("uname");
	String ucity = request.getParameter("ucity");
	
	Date date = new Date();

	out.println("Hello " + name);
%>

<h1><%= date %></h1>
<h2><%= ucity %></h2>
</body>
</html>