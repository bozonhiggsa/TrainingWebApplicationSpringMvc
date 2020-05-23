<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Registration form</title>
    <link href="https://fonts.googleapis.com/css2?family=Fira+Sans:wght@400;600&display=swap" rel="stylesheet">
    <link href=<c:url value="/static/bootstrap.min.css"/> rel="stylesheet">
    <link href=<c:url value="/static/common.css"/> rel="stylesheet">
</head>

<body>

<div class="container">
    <c:if test="${matchUser==true}">
        <div class="row">
            <div class="col-lg-12">
                <h3>The same user already exists!</h3>
            </div>
        </div>
    </c:if>
    <c:if test="${incorrectRegistration==true}">
        <div class="row">
            <div class="col-lg-12">
                <h3>Registration data are incorrect!</h3>
            </div>
        </div>
    </c:if>

    <h4 class="text-center">Please fill in the information below to create your account</h4>

    <form method="POST" action="/createUser" class="form-signin" accept-charset="UTF-8">
        <div class="form-group">
            <h5 class="text-left">Login</h5>
            <input name="login" type="text" class="form-control" placeholder="Enter your login" autofocus="true" required/>
            <h5 class="text-left">Password</h5>
            <input name="password" type="password" class="form-control" placeholder="Enter your password" required/>
            <h5 class="text-left">Name</h5>
            <input name="name" type="text" class="form-control" placeholder="Enter your name" required/>
            <h5 class="text-left">Surname</h5>
            <input name="lastname" type="text" class="form-control"/>
            <h5 class="text-left">Email</h5>
            <input name="email" type="text" class="form-control" placeholder="Enter your e-mail" required/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </div>
    </form>
</div>
</body>
</html>