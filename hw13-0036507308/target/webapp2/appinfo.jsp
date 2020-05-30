<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%! 
public String formatInterval(long interval) {
	final long day = TimeUnit.MILLISECONDS.toDays(interval);
	final long hr = TimeUnit.MILLISECONDS.toHours(interval)%24;
    final long min = TimeUnit.MILLISECONDS.toMinutes(interval) %60;
    final long sec = TimeUnit.MILLISECONDS.toSeconds(interval) %60;
    final long ms = TimeUnit.MILLISECONDS.toMillis(interval) %1000;

    return String.format("%02d:%02d:%02d:%02d:%02d", day, hr, min, sec, ms);
}
%>
<!DOCTYPE html>
<html>
<head>
<title>App info</title>
</head>
<body bgcolor = #<%= (String)session.getAttribute("pickedBgCol") == null ? "FFFFFF" : 
			(String)session.getAttribute("pickedBgCol")%>>
<%= ("Time passed: " + formatInterval(
		System.currentTimeMillis() - (long)(request.getServletContext().getAttribute("startTime")))) %>
</body>
</html>