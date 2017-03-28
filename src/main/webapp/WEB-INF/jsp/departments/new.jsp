<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Add new department</title>
    <link href="<c:url  value="/resources/style.css" />" rel="stylesheet">
</head>
<body>
    <h2>Add new department</h2>
    <div id="list">
        <sf:form method="post" action="departments" modelAttribute="department">
            <ul>
                <li>
                    <label for="name">Name:</label>
                    <sf:input path="name" id="name"/>
                </li>
               
                <li>
                    <input type="submit" value="Save" id="save" />
                </li>
            </ul>
        </sf:form>
    </div>

    <br /><br />
    <a href="../departments">Go Back</a>
</body>
</html>