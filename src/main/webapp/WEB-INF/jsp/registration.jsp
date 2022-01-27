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
        <input class="form-control" type="text" name="login" value="" />

    </div>
    <div class="form-group">
        <label>Password</label>
        <input class="form-control" type="password" name="password" value="" />
    </div>
    <div class="form-group">
        <label>First Name</label>
        <input class="form-control" type="text" name="firstName" value="" />
    </div>
    <div class="form-group">
        <label>Last Name</label>
        <input class="form-control" type="text" name="lastName" value="" />
    </div>
    <div class="form-group">
        <label>City</label>
        <input class="form-control" type="text" name="city" value="" />
    </div>
    <div class="form-group">
        <label>Street</label>
        <input class="form-control" type="text" name="street" value="" />
    </div>
    <div class="form-group">
        <label> State</label>
        <input class="form-control" type="text" name="state" value="" />
    </div>
    <div class="form-group">
        <label>Zip</label>
        <input class="form-control" type="text" name="zip" value="" />
    </div>
    <div class="form-group">
        <label>Phone</label>
        <input class="form-control" type="text" name="phone" value="" />
    </div>
    <div class="form-group">
        <label>Email</label>
        <input class="form-control" type="text" name="email" value="" />
    </div>

    <input class="btn" type="submit" value="Sign up" />
    <h4>${errorMessage}</h4>
</form>
</div>

<a href="${pageContext.request.contextPath}/welcome.jsp">back to main</a>


</body>
</html>
