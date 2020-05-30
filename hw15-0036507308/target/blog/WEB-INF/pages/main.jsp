<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dobrodošli!</title>
<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
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
<h1>
		Login
</h1>

<form action="main" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${zapis.nick}"/>' size="20">
		 </div>
		 <c:if test="${zapis.imaPogresku('nick')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value='<c:out value=""/>' size="50">
		 </div>
		 <c:if test="${zapis.imaPogresku('password')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('password')}"/></div>
		 </c:if>
		</div>


		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Login">
		</div>
</form>

<h1>Registracija korisnika</h1>
<a href="register">Registracija</a>

<h1>Popis autora</h1>
	<ol>
		<c:forEach var="e" items="${authors}">
			<li><a href="author/${e.nick}">${e.nick}</a></li>
		</c:forEach>
	</ol>


<a href="logout">Odjava</a>

</body>
</html>