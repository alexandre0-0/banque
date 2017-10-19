<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>Liste des clients du commercial ...</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Liste des clients du commercial ...</h1>
        <table class="table table-striped" >
            <thead>
                <tr>	
                    <th>N° client</th>
                    <th>Nom</th>
                    <th>Email</th>
                    <th>Commentaire</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${clients}" var="client">
                    <tr>
                        <td>${client.noClient} </td>
                        <td>${client.nom}</td>
                        <td>${client.email}</td>
                        <td>${client.commentaire}</td>
                        <td>
                            <a href="comptes?noClient=${client.noClient}">Comptes</a>
                            <a href="creerCompte?noClient=${client.noClient}">Créer un compte</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>		
        </table>


        <c:if test="${msg!= null}">
            <span class="text-danger">${msg}</span>
        </c:if>
    </body>
</html>
