<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <title>Departments</title>
        
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
    </head>
    <body>
        <h1>List of departments</h1>
        <a href="departments?new">Add new department</a>
        <table cellspacing="5" class="main-table">
            <tr>
                <th>Name</th>
                <th>Details</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="#{departments}" var="dep">
                <tr>
                    <td>${dep.name}</td>
                    <td>
                        <a href="departments/${dep.id}">Go to page</a>
                    </td>
                    <td>
                        <form action="departments/del${dep.id}" method="post" cssClass="delete">
                            <input type="submit" class="delete-button" value="" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <br />
        <a href="welcome">Go to main page</a>
    </body>
</html>