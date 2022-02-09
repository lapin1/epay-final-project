<%--
  Created by IntelliJ IDEA.
  User: lapin
  Date: 6.02.22
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>User Information</title>
    <link href="./css/header.css" rel="stylesheet" type="text/css">
    <link href="./css/accountPageStyle.css" rel="stylesheet" type="text/css">

    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="locale" var="loc" />

    <fmt:message bundle="${loc}" key="locale.en.button" var="en_btn" />
    <fmt:message bundle="${loc}" key="locale.ru.button" var="ru_btn" />
    <fmt:message bundle="${loc}" key="locale.hi.greeting" var="hi" />
    <fmt:message bundle="${loc}" key="locale.about" var="about" />


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
                    <li><a href="MyController?command=showUserInfo&id=${idOfUser}">${userName}</a></li>
                    <li><a href="MyController?command=logout">logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
    </div>
</header>
<h1>${hi}, ${userName}</h1>
<h2></h2>
<div>

    login${login}
    firstName${userInfo.getFirstName()}
        lastName${userInfo.getLastName()}
        city${userInfo.getCity()}
        streer${userInfo.getStreet()}
        state${userInfo.getState()}
        zip${userInfo.getZip()}
        phone${userInfo.getPhone()}
        email${userInfo.getEmail()}
</div>

</body>
</html>
