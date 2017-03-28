<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="manager" type="org.timesheet.domain.Manager"--%>
<%--@elvariable id="tasks" type="java.util.List<org.timesheet.domain.Task>"--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tasks for manager</title>
        <link rel="stylesheet" href="/timesheet-app/resources/style.css" type="text/css" />
    </head>
    <body>
        <h1>Tasks for manager</h1>
        <br /><br />

        <h3 >Current manager:<a href="/timesheet-app/managers/${manager.id}" id="curent-manger">
                ${manager.name}</a>
        </h3>
        <label>Managerr's tasks</label>
        <c:forEach items="${tasks}" var ="task" >
            <a href="/timesheet-app/tasks/${task.id}">${task.description}</a><br/>
        </c:forEach>
    </body>
</html>
