<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Sign in</title>
    <link href="./css/signin.css" rel="stylesheet" type="text/css">

    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="locale" var="loc" />

    <fmt:message bundle="${loc}" key="locale.sign.in.label" var="sign_in" />
    <fmt:message bundle="${loc}" key="locale.username" var="username" />
    <fmt:message bundle="${loc}" key="locale.password" var="password" />
    <fmt:message bundle="${loc}" key="locale.sign.in.button" var="sign_in_btn" />
    <fmt:message bundle="${loc}" key="locale.back.to.main.btn" var="back_to_main" />
    <fmt:message bundle="${loc}" key="locale.en.button" var="en_btn" />
    <fmt:message bundle="${loc}" key="locale.ru.button" var="ru_btn" />
    

</head>

<div class="container">

    <form action="MyController" method="get">
        <h1>${sign_in}</h1>
        <input type="hidden" name="command" value="authorization">
        <div>
            <label>${username}</label>
            <input class="form-control" type="text" name="login" value=""/>

        </div>
        <div>
            <label>${password}</label>
            <input class="form-control" type="password" name="password" value=""/>
        </div>
        <input class="btn" type="submit" value="${sign_in_btn}"/>
        <h4>${errorOfValidation.get(2)}</h4>
    </form>
</div>

<a href="${pageContext.request.contextPath}/welcome.jsp">${back_to_main}</a>

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
