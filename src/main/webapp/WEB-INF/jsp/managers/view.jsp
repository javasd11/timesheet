<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Manager page</title>
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
    </head>
    <body>
        <h2>Manager info</h2>
        <div id="list">
            <sf:form action="" commandName="manager" method="post">
                <p>
                    <label for="name">Name:</label>
                    <sf:input name="name" id="name" path="name" disabled="true"></sf:input>
                    </p>
                    <p>
                        <input type="button" value="Unlock" id="unlock" />
                        <input type="submit" value="Save" id="save" class="hidden" />
                    </p>
            </sf:form>

            
        </div>

        <br /><br />
        <a href="../managers">Go Back</a>

        <script src="<c:url value="/resources/jquery-1.7.1.js" />"></script>
        <script>
            (function () {
                $("#unlock").on("click", function () {
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