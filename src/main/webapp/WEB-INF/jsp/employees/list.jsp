<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Employees</title>
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
    </head>
    <body>
        <h1>List of employees</h1>
        <a href="employees?new">Add new employee</a>
        <table cellspacing="5" class="main-table">
            <tr>
                <th>Name</th>
                <th>Department</th>
                <th class ="tab-width" >Details </th>
                <th>Delete</th>
            </tr>
            <c:forEach items="#{employees}" var="emp">
                <tr>
                    <td>${emp.name}</td>                    
                    <td> <a href="<c:url value="/departments/${emp.department.id}"/>">${emp.department.name}</a></td>
                    <td> <a href="employees/${emp.id}">Go to page</a> </td>
                    <td class="tab-width"> 
                        <form action="employees/del${emp.id}" method="post" cssClass="delete">
                            <input type="submit" class="delete-button" value="" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <br />
        <a href="welcome">Go back</a>
    </body>
</html>