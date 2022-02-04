<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
    <link href="./css/signup.css" rel="stylesheet" type="text/css">
</head>
<p>
<div class="container">
<form action="MyController" method="post">
    <h1>Registration</h1>
    <input type="hidden" name="command" value="registration">

    <div class="form-group">
        <label>Username</label>
        <input class="form-control" type="text" name="login" value="${user.getLogin()}" />
        <h5 style="color: red">${errorRegList.get("loginError")}</h5>
    </div>
    <div class="form-group">
        <label>Password</label>
        <input class="form-control" type="password" name="password" value="" />
        <h5 style="color: red">${errorRegList.get("passwordError")}</h5>
    </div>
    <div class="form-group">
        <label>First Name</label>
        <input class="form-control" type="text" name="firstName" value="" />
        <h5 style="color: red">${errorRegList.get("firstNameError")}</h5>
    </div>
    <div class="form-group">
        <label>Last Name</label>
        <input class="form-control" type="text" name="lastName" value="" />
        <h5 style="color: red">${errorRegList.get("lastNameError")}</h5>
    </div>
    <div class="form-group">
        <label>City</label>
        <input class="form-control" type="text" name="city" value="" />
        <h5 style="color: red">${errorRegList.get("cityError")}</h5>
    </div>
    <div class="form-group">
        <label>Street</label>
        <input class="form-control" type="text" name="street" value="" />
        <h5 style="color: red">${errorRegList.get("streetError")}</h5>
    </div>
    <div class="form-group">
        <label> State</label>
        <input class="form-control" type="text" name="state" value="" />
        <h5 style="color: red">${errorRegList.get("stateError")}</h5>
    </div>
    <div class="form-group">
        <label>Zip</label>
        <input class="form-control" type="text" name="zip" value="" />
        <h5 style="color: red">${errorRegList.get("zipError")}</h5>
    </div>
    <div class="form-group">
        <label>Phone</label>
        <input class="form-control" type="text" name="phone" value="" />
        <h5 style="color: red">${errorRegList.get("phoneError")}</h5>
    </div>
    <div class="form-group">
        <label>Email</label>
        <input class="form-control" type="text" name="email" value="" />
        <h5 style="color: red">${errorRegList.get("emailError")}</h5>
    </div>

    <input class="btn" type="submit" value="Sign up" />
    <h4>${errorMessage}</h4>
</form>
</div>

<a href="${pageContext.request.contextPath}/welcome.jsp">back to main</a>


</body>
</html>
