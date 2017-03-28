<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Department page</title>      
    <!--<link href="<c:url value="/resources/style.css" />" rel="stylesheet">-->
    <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
</head>
<body>
    <h2>Department info</h2>
    <div id="list">
        <sf:form method="post" modelAttribute="department" >
            <ul>
                <li>
                    <label for="name">Name:</label>
                    <sf:input path="name" id="name" disabled="true"/>
                </li>
                <li>
                    <input type="button" value="Unlock" id="unlock" />
                    <input type="submit" value="Update" id="save" class="hidden" />
                </li>
            </ul>
        </sf:form>
    </div>

    <br /><br />
    <a href="../departments">Go Back to departments</a>

    <!--<script src="resources/jquery-1.7.1.js"></script>-->
    <script src="<c:url value="/resources/jquery-1.7.1.js" />"></script>
    <script>
        (function() {
            $("#unlock").on("click", function() {
                $("#unlock").addClass("hidden");

                // enable stuff
                $("#name").removeAttr("disabled");
                $("#department").removeAttr("disabled");
                $("#save").removeClass("hidden");
            });
        })();
    </script>
</body>
</html>