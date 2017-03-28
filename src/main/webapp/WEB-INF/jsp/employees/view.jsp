<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Employee page</title>
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
    </head>
    <body>
        <h2>Employee info</h2>
        <div id="list">
            <sf:form modelAttribute="employee" method="post">
                <ul>
                    <li>
                        <label for="name">Name:</label>
                        <sf:input  id="name" path="name" disabled="true"/>
                    </li>

                    <li>
                        <label>Department</label>
                        <sf:select path="department" id="select-departments" disabled ="true" >
                            <c:forEach items="${departments}" var="dep" varStatus="status">
                                <c:choose>
                                    <c:when test="${employee.department eq dep}">
                                    <option value="${dep.id}" selected="true">${dep.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${dep.id}" >${dep.name}</option>
                                </c:otherwise>
                            </c:choose> 
                        </c:forEach>
                    </sf:select>
                    </li>
                    <li>
                        <input type="button" value="Unlock" id="unlock" />
                        <input type="submit" value="Save" id="save" class="hidden" />
                    </li>
                </ul>
            </sf:form>
        </div>

        <br /><br />
        <a href="../employees">Go Back</a>

        <script src="<c:url value="/resources/jquery-1.7.1.js" />"></script>
        <script>
            (function () {
                $("#unlock").on("click", function () {
                    $("#unlock").addClass("hidden");

                    // enable stuff
                    $("#name").removeAttr("disabled");
                    $("#department").removeAttr("disabled");

                    $("#save").removeClass("hidden");
                    $("#select-departments").removeAttr("disabled");
                });
            })();
        </script>
    </body>
</html>