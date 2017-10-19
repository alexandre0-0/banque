<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>Liste des comptes du client ${noClient}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Comptes du client ${noClient}</h1>
        <table class="table table-striped" >
            <thead>
                <tr>	
                    <th>Numéros du compte</th>
                    <th>Solde du compte</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${comptes}" var="compte">
                    <tr>
                        <th>${compte.noCompte} </th>
                        <th>${compte.solde}</th>
                    </tr>
                </c:forEach>
            </tbody>		
        </table>


        <c:if test="${msg!= null}">
            <span class="text-danger">${msg}</span>
        </c:if>
    </body>
</html>
