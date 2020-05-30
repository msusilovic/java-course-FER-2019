<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor = #<%= (String)session.getAttribute("pickedBgCol") == null ? "FFFFFF" : 
			(String)session.getAttribute("pickedBgCol")%>>

	<h1>Rezultati glasovanja</h1>
	<p>Ovo su rezultati glasovanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="e" items="${results}">
			<tr><td>${e.bandName}</td><td>${e.count}</td></tr>
		</c:forEach>
		</tbody>
	</table>
	
	<h2>Grafički prikaz rezultata</h2>
 	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	
	<h2>Rezultati u XLS formatu</h2>
 	<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var = "e" items = "${winners}">
		<li><a href="${e.song}" target="_blank">${e.name}</a></li> 
		</c:forEach>
	</ul>


</body>
</html>
