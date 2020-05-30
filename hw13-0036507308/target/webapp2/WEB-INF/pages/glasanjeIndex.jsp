<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Glasovanje</title>
</head>
<body bgcolor = #<%= (String)session.getAttribute("pickedBgCol") == null ? "FFFFFF" : 
			(String)session.getAttribute("pickedBgCol")%>>
	<h1>Glasovanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na	link kako biste glasovali!</p>
	<ol>
		<c:forEach var="e" items="${bands}">
			<li><a href="glasanje-glasaj?id=${e.id}">${e.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>