<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link href="css/welcome.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>ePay</title>
    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="locale" var="loc" />

    <fmt:message bundle="${loc}" key="locale.greeting" var="greeting" />
    <fmt:message bundle="${loc}" key="locale.en.button" var="en_btn" />
    <fmt:message bundle="${loc}" key="locale.ru.button" var="ru_btn" />
    <fmt:message bundle="${loc}" key="locale.sign.in.button" var="sign_in_btn" />
    <fmt:message bundle="${loc}" key="locale.sign.up.link" var="sign_up_link" />
    <fmt:message bundle="${loc}" key="locale.no.acc.message" var="no_acc_message" />
</head>
<body>
<p class="name">${greeting}</p>


<br/>
<form  id="sign-in" action="MyController" method="get">
    <input type="hidden" name="command" value="GO_TO_AUTHORIZATION_PAGE">
    <button class="go-to-sign" type="submit" form="sign-in">${sign_in_btn}</button>
</form>
<p class="reg">
${no_acc_message} &nbsp  <a href="MyController?command=GO_TO_REGISTRATION_PAGE">${sign_up_link}</a>
</p>

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
