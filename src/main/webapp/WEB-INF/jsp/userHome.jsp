<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>My Accounts</title>
    <link href="./css/home.css" rel="stylesheet" type="text/css">
    <link href="./css/header.css" rel="stylesheet" type="text/css">

    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="locale" var="loc" />

    <fmt:message bundle="${loc}" key="locale.en.button" var="en_btn" />
    <fmt:message bundle="${loc}" key="locale.ru.button" var="ru_btn" />
    <fmt:message bundle="${loc}" key="locale.account.label" var="account_label" />
    <fmt:message bundle="${loc}" key="locale.balance" var="balance" />
    <fmt:message bundle="${loc}" key="locale.status" var="status" />
    <fmt:message bundle="${loc}" key="locale.hi.greeting" var="hi" />
    <fmt:message bundle="${loc}" key="locale.my.accounts.label" var="myaccounts" />
    <fmt:message bundle="${loc}" key="locale.create.new.button" var="create_btn" />


</head>
<body>
<header>
        <div id="left">
            ePay
        </div>
        <div id="right">
            <ul>
                <li><a href="#">
                    <div class="burger"></div>
                    <div class="burger"></div>
                    <div class="burger"></div>
                </a>
                    <ul>
                        <li><a id="1" href="/about">${userName}</a></li>
                        <li><a href="MyController?command=logout">logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</header>
<h1>${hi}, ${userName}</h1>
<h2>${myaccounts}</h2>

<div class="box">
<c:forEach var ="account" varStatus="countOfaccounts" items="${accounts}">
    <c:choose>
        <c:when test="${account.status eq 'BLOCKED'}">
    <a class="account" style="cursor: not-allowed" onclick="return false" href="MyController?command=GO_TO_SELECTED_ACCOUNT&id=<c:out value="${account.id}"/>">
        <br />
        </c:when>
        <c:otherwise>
        <a class="account" href="MyController?command=GO_TO_SELECTED_ACCOUNT&id=<c:out value="${account.id}"/>">
            <br />
        </c:otherwise>
    </c:choose>


    <div class="account-content">
        <br>
            id:${account.id}
        <br>
            ${account_label} №:${account.accountNumber}
        <br>
            ${balance}:${account.balance}
        <br>
            ${status}:${account.status}
    </div>
     </a>
    <c:set var="count" value="${countOfaccounts.count}"/>

</c:forEach>
</div>

<div class="for-btn">
    <form action="MyController" method="post">
        <input type="hidden" name="command" value="accountCreation">
        <input class="btn" ${count == 3 ? 'disabled' : ''} type="submit" value="${create_btn}" />
    </form>
</div>

<div>
    <p>
    <form action="MyController" method="get">
        <input type="hidden" name="command" value="changeLanguage" />
        <input type="hidden" name="locale" value="ru" />
        <input type="submit" value="${ru_btn}" />
    </form>
    <form action="MyController" method="get">
        <input type="hidden" name="command" value="changeLanguage" />
        <input type="hidden" name="locale" value="en" />
        <input type="submit" value="${en_btn}" />
    </form>
    </p>
</div>

</body>
</html>
