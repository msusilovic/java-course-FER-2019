<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Novi zapis</title>
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
<form action="new" method="post">

		<div>
		 <div>
		  <span class="formLabel">Naslov</span><input type="text" name="title" value='<c:out value="${form.title}"/>' size="50">
		 </div>
		 <c:if test="${form.hasError('title')}">
		 <div class="greska"><c:out value="${form.getError('title')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Tekst</span><input type="text" name="text" value='<c:out value="${form.text}"/>' size="50">
		 </div>
		 <c:if test="${form.hasError('text')}">
		 <div class="greska"><c:out value="${form.getError('text')}"/></div>
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