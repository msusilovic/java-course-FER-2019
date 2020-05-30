<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OS usage report</title>
</head>
<body bgcolor = #<%= (String)session.getAttribute("pickedBgCol") == null ? "FFFFFF" : 
			(String)session.getAttribute("pickedBgCol")%>>
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed</p>
	<img src = "reportImage">
</body>
</html>