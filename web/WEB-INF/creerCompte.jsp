<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<c:set var="noClient" value="1" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
                <title>Créer un compte pour le client ${param["noClient"]}</title>
	</head>
	<body>
	
	<form method="post">
            <input type="hidden" name="noClient" value="${param["noClient"]}"/>
            <fieldset>
			<legend>Créer un compte pour le client ${param["noClient"]}</legend>
		</fieldset>
		<label>
			montant initial
			<input type="text" name="valeur" />
		</label>
		<button class="btn btn-warning" type="submit">Créer le compte</button>
	</form>
	<br/>
	<c:if test="${msgErreur != null}">
		<span class="text-danger">${msgErreur}</span>
	</c:if>
	<c:if test="${msgSucces != null}">
		<span class="text-success">${msgSucces}</span>
	</c:if>
</body>
</html>
