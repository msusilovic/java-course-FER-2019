<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Popis za odabranog autora</title>
</head>
<body>
<header>
   <c:if test="${sessionScope[\"current.user.id\"]!=null}">
        <h3>Ime: ${sessionScope["current.user.fn"]}</h3>
        <h3>Prezime: ${sessionScope["current.user.ln"]}</h3>
        <h3><a href="logout">Odjava</a></h3>
        <br>
        <br>
    </c:if>
</header>
<ol>
		<c:forEach var="e" items="${entries}">
			<li><a href="${e.creator.nick}/${e.id}">${e.title}</a></li>
		</c:forEach>
	</ol>

<hr>
 <c:if test="${sessionScope[\"current.user.id\"].equals(author.id)}">
        <h3><a href= "${author.nick}/new">Novi</a></h3>
        <br>
        <br>
    </c:if>
</body>
</html>