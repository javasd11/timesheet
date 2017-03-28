<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Timesheet Service</title>
        <link href="<c:url value="/resources/style.css" />" rel="stylesheet">
    </head>
    <body>
        <h1>Timesheet service</h1>
        <div id="list">
            <h3>Busiest task</h3>
            <ul>
                <li>
                    <a href="<c:url value="/tasks/${busiestTask.id}"/>" id="busiest-task">
                        ${busiestTask.description}</a>
                </li>
            </ul>

            <h3>Tasks for manager</h3>
            <sf:form method="get" id="manager-form" >
                <ul>
                    <li>
                        <select id="select-managers">
                            <c:forEach items="${managers}" var ="man" >
                                <option value="${man.id}">${man.name}</option>
                            </c:forEach>
                        </select>
                    </li>
                    <li>
                        <input type="submit" value="Search"/>
                    </li>
                </ul>
            </sf:form>

            <h3>Tasks for employee</h3>
            <sf:form method="get" id="employees-form" >
                <ul>
                    <li>
                        <select id="select-employees">
                            <c:forEach items="${employees}" var ="emp" >
                                <option value="${emp.id}">${emp.name}</option>
                            </c:forEach>
                        </select>
                    </li>
                    <li>
                        <input type="submit" value="Search"/>
                    </li>
                </ul>
            </sf:form>
        </div>
        <br/> <br />

        <a href="welcome">Go back</a>
        <script src="<c:url value="/resources/jquery-1.7.1.js" />"></script>
        
        <script type="text/javascript">
            (function(){
                setAddAction("#select-managers","#manager-form", "manager-tasks");
                setAddAction("#select-employees","#employees-form", "employee-tasks");
                
                $("#select-managers").on("change", function(){
                    setAddAction("#select-managers", "#manager-form", "managers-tasks")
                });
                $("#select-employees").on("change", function(){
                    setAddAction("#select-employees", "#employee-form", "employee-tasks")
                });
                
                function setAddAction(selectName, formName, action){
                    var id =$(selectName).val();
                    $(formName).attr("action","/timesheet-app/timesheet-service/"+action+"/"+id);
                }
            })();
            
        </script>
    </body>
</html>
