<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="manager" type="org.timesheet.domain.Manager"--%>
<%--@elvariable id="tasks" type="java.util.List<org.timesheet.domain.Task>"--%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tasks for employee</title>
        <link rel="stylesheet" href="${initParam.homePath}/resources/style.css" type="text/css" />
    </head>
    <body>
        <h3>Current employee:<a href="${initParam.homePath}/employees/${employee.id}" >${employee.name}</a></h3>

        <label>Employees's tasks</label>
        <c:forEach items="${tasks}" var ="task" >
            <a href="${initParam.homePath}/tasks/${task.id}">${task.description}</a><br/>
        </c:forEach>
    </body>
</html>
