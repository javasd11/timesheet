<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--@elvariable id="task" type="org.timesheet.domain.Task"--%>
<%--@elvariable id="managers" type="java.util.List<org.timesheet.domain.Manager"--%>

<html>
    <head>
        <title>Add new task</title>
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">      
    </head>

    <body>
        <h2> Add new Task</h2>
        <div id="list">
            <form method="post" action="tasks" commandName="task">
                <ul>
                    <li>
                        <label for="description"> Description</label>
                        <input name="description" id="description" value="${task.description}"/>
                    </li>
                    
                    <li>
                        <label for="manager-select">Manager:</label>
                        <sf:select path="task.manager" id="manager-select">
                            <sf:options items="${managers}" itemLabel="name" itemValue="id" />
                        </sf:select>
                    </li>
                    <li>
                        Employees will be genarated ...
                    </li>
                    <li>
                        <input type="submit" value="Safe">
                    </li>

                </ul>
            </form>
        </div>
        <br><br>
        <a href="tasks"> View all tasks</a>
    </body>
</html>