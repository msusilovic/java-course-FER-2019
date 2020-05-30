<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" session = "true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Colors</title>
</head>
<body bgcolor = #<%= (String)session.getAttribute("pickedBgCol") == null ? "FFFFFF" : 
			(String)session.getAttribute("pickedBgCol")%>>
				
	<p><a href="index.jsp">Home</a></p>
	<p><a href="setcolor?pickedBgCol=FF0000">RED</a></p>
	<p><a href="setcolor?pickedBgCol=00FF00">GREEN</a></p>
	<p><a href="setcolor?pickedBgCol=00FFFF">CYAN</a></p>
	<p><a href="setcolor?pickedBgCol=FFFFFF">WHITE</a></p>

	
</body>
</html>