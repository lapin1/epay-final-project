<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User accounts</title>
</head>
<body>
<h1>User accounts</h1>
<c:forEach var ="account" items="${accounts}">
    <tr style="align-content: center">
        <td>id <c:out value="${account.id}"/></td>
        <br>
        <td>number <c:out value="${account.accountNumber}"/></td>
        <br>
        <td>balance <c:out value="${account.status}"/></td>
        <br>

        <td><a href="MyController?command=unblockAccount&blockedAccountId=<c:out value="${account.id}"/>">>unblock account</a></td>

        <br>
        <br>
    </tr>
</c:forEach>

</body>
</html>
