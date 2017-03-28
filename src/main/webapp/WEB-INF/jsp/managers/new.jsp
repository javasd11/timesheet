<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new manager</title>
    <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
</head>
<body>
    <h2>Add new Manager</h2>
    <div id="list">
        <sf:form method="post" action="managers" commandName="manager">
            <ul>
                <li>
                    <label for="name">Name:</label>
                    <sf:input name="name" id="name" path="name"></sf:input>
                </li>
               
                <li>
                    <input type="submit" value="Save" id="save" />
                </li>
            </ul>
        </sf:form>
    </div>

    <br /><br />
    <a href="managers">Go Back</a>
</body>
</html>