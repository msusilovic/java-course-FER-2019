<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Trigonometry</title>
</head>
<body bgcolor = #<%= (String)session.getAttribute("pickedBgCol") == null ? "FFFFFF" : 
			(String)session.getAttribute("pickedBgCol")%>>
	<table border = "1">
		<tbody>
			<tr>
				<th>Sin</th>
			</tr>
			
			<c:forEach var="e" items="${sinValues}">
			<tr><td>${e.key}</td><td>${e.value}</td></tr>
			</c:forEach>
			
		</tbody>
	</table>
	<table border = "1">
		<tbody>
			<tr>
				<th>Cos</th>
			</tr>
			
			<c:forEach var="e" items="${cosValues}">
			<tr><td>${e.key}</td><td>${e.value}</td></tr>
			</c:forEach>
			
		</tbody>
	</table>
</body>
</html>