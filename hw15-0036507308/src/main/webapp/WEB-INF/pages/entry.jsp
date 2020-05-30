<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
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
<c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>
    </c:otherwise>
  </c:choose>
  <hr>


	<form action="<%out.write(request.getContextPath());%>/servleti/author/${blogEntry.creator.nick}/${blogEntry.id}" method="post">

		<div>
			<div>
				<span class="formLabel">Dodaj komentar</span><input type="text"
					name="message" value='<c:out value="${form.message}"/>' size="100">
			</div>
			<c:if test="${form.hasError('message')}">
				<div class="greska">
					<c:out value="${form.getError('message')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
			 <c:if test="${sessionScope[\"current.user.id\"]==null}">
				<span class="formLabel">Upiši mail</span><input type="text"
					name="email" value='<c:out value="${form.email}"/>' size="50">
			 </c:if>
			</div>
			<c:if test="${form.hasError('email')}">
				<div class="greska">
					<c:out value="${form.getError('email')}" />
				</div>
			</c:if>
		</div>
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> 
				<input type="submit"name="metoda" value="Pohrani"> 
				<input type="submit" name="metoda" value="Odustani">
		</div>
	</form>
	<hr>
	
  <c:if test="${sessionScope[\"current.user.id\"].equals(author.id)}">
  <form action="edit" method="get">
   <input type="hidden" name="id" value="${blogEntry.id}"> 
<input type="submit" value = "Uredi">   
</form>

        <br>
        <br>
    </c:if>
</body>
</html>