<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Odaberite najdražu opciju:</title>
</head>
<body>
<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
		<c:forEach var="e" items="${options}">
			<li><a href="glasanje-glasaj?id=${e.id}">${e.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>