<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registracija korisnika</title>
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
<form action="register" method="post">

		<div>
		 <div>
		  <span class="formLabel">Ime</span><input type="text" name="firstName" value='<c:out value="${zapis.firstName}"/>' size="50">
		 </div>
		 <c:if test="${zapis.hasError('firstName')}">
		 <div class="greska"><c:out value="${zapis.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Prezime</span><input type="text" name="lastName" value='<c:out value="${zapis.lastName}"/>' size="50">
		 </div>
		 <c:if test="${zapis.hasError('lastName')}">
		 <div class="greska"><c:out value="${zapis.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Email</span><input type="text" name="email" value='<c:out value="${zapis.email}"/>' size="50">
		 </div>
		 <c:if test="${zapis.hasError('email')}">
		 <div class="greska"><c:out value="${zapis.getError('email')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Korisničko ime</span><input type="text" name="nick" value='<c:out value="${zapis.nick}"/>' size="50">
		 </div>
		 <c:if test="${zapis.hasError('nick')}">
		 <div class="greska"><c:out value="${zapis.getError('nick')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Lozinka</span><input type="password" name="password" value='<c:out value="${zapis.password}"/>' size="50">
		 </div>
		 <c:if test="${zapis.hasError('password')}">
		 <div class="greska"><c:out value="${zapis.getError('password')}"/></div>
		 </c:if>
		</div>
		
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>
</body>
</html>