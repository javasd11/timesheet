<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add new employee</title>
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
    </head>
    <body>
        <h2>Add new Employee</h2>
        <div id="list">
            <sf:form method="post" action="employees"  modelAttribute="employee">
                <ul>
                    <li>
                        <label for="name">Name:</label>
                        <input name="name" id="name" value="${employee.name}"/>
                    </li>
                    <li>
                        <label>Department</label>
                        <sf:select path="department" >
                            <sf:options items="${departments}" itemLabel="name" itemValue="id" />
                        </sf:select>
                    </li>
                    <li>
                        <input type="submit" value="Save" id="save" />
                    </li>
                </ul>
            </sf:form>
        </div>

        <br /><br />
        <a href="employees">Go Back</a>
    </body>
</html>