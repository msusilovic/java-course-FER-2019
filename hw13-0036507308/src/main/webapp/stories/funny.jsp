<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%! 
private String getRandomColor() {
	String[] colors = {"#FFFF00", "#000000", "#0000FF", "FF0000", "#FF00FF"};
	Random r = new Random();
	int value = r.nextInt(5);
	
	return colors[value];
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Funny story</title>
</head>
<body bgcolor = #<%= (String)session.getAttribute("pickedBgCol") == null ? "FFFFFF" : 
			(String)session.getAttribute("pickedBgCol")%>>
<font color=<%=getRandomColor() %>>Funny story!</font>
</body>
</html>