<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<%--@elvariable id="task" type="org.timesheet.domain.Task"--%>
<%--@elvariable id="managers" type="java.util.List<org.timesheet.domain.Manager"--%>

<html>
    <head>
        <title>Add new timesheet</title>
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">      
    </head>

    <body>
        <h2> Add new Timesheet</h2>
        <div id="list">
            <form method="post" action="timesheets" commandName="timesheet">
                <ul>
                    <li>
                        <label for="employees">Select employee:</label>
                        <sf:select path="timesheet.who" id="employees">
                            <sf:options items="${employees}" itemLabel="name" itemValue="id" />
                        </sf:select>
                    </li>
                    <li>
                        <label for="tasks">Select task:</label>
                        <sf:select path="timesheet.task" id="tasks">
                            <sf:options items="${tasks}" itemLabel="description" itemValue="id" />
                        </sf:select>
                        
                        
                        
                        
                    </li>
                    <li>
                        <label for="hours">Hours:</label>
                        <input name="hours" id="hours" value="${timesheet.hours}"/>
                    </li>
                    <li>
                        <input type="submit" value="Save"> 
                    </li>
                </ul>
            </form>
        </div>
        <br><br>
        <a href="timesheets"> View all timesheets</a>
    </body>
</html>