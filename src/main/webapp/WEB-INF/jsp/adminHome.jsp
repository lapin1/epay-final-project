<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <h2><a href="MyController?command=logout">logout</a></h2>
</head>
<body>
<h1>admin page</h1>

<c:forEach var ="user" items="${users}">
    <tr style="align-content: center">
        <td>id <c:out value="${user.id}"/></td>
        <br>
        <td>login <c:out value="${user.login}"/></td>
        <br>
        <td>password <c:out value="${user.password}"/></td>
        <br>
        <td><a href="MyController?command=goToUsersAccount&id=<c:out value="${user.id}"/>">view accounts</a></td>
        <br>
        <br>
    </tr>
</c:forEach>

</body>
</html>
