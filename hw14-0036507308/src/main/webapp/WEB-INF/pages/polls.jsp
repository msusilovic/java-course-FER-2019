<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Izbor ankete</h1>
	<p>Odaberite anketu.</p>
	<ol>
		<c:forEach var="e" items="${polls}">
			<li><a href="glasanje?pollID=${e.id}">${e.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>