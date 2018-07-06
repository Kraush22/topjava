


<%--
  Created by IntelliJ IDEA.
  User: Рау
  Date: 06.07.2018
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {color: lime}
        .exceeded {color: red}
    </style>
</head>
<body>
<section>
    <h1><a href="index.html">HOME</a></h1>
    <h4>MEALS</h4>
    <table border="1" >
        <thead>
        <tr>
            <th>DateTime</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        </thead>

        <c:forEach var="meal" items="${meals}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal' }">
                <td>${meal.dateTime}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>