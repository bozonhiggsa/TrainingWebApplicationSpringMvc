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

    <title>Login page</title>
    <link href="https://fonts.googleapis.com/css2?family=Fira+Sans:wght@400;600&display=swap" rel="stylesheet">
    <link href=<c:url value="/static/bootstrap.min.css"/> rel="stylesheet">
    <link href=<c:url value="/static/common.css"/> rel="stylesheet">
</head>

<body>

<div class="container">
    <c:if test="${incorrectLogin==true}">
        <div class="row">
            <div class="col-lg-12">
                <h3>Incorrect login or password!</h3>
            </div>
        </div>
    </c:if>

    <form method="POST" action="/login" class="form-signin" accept-charset="UTF-8">
        <h4 class="text-left">Log in</h4>

        <div class="form-group">
            <input name="login" type="text" class="form-control" placeholder="Enter your login" autofocus="true" required/>
            <input name="password" type="password" class="form-control" placeholder="Enter your password" required />

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            <h4 class="text-center refer"><a href="/registrationPage">Create an account</a></h4>
        </div>
    </form>

    <form method="GET" action="/" class="form-signin">
        <div class="form-group">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Enter as a guest</button>
        </div>
    </form>
</div>
</body>
</html>