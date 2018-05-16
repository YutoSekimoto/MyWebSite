<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>ユーザー登録</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/User/UserTouroku.css">
</head>

<body>

    <div class="wrapper">

    <c:if test="${eM != null}">

    <div class="alert alert-danger" role="alert">
    <c:out value="${eM}" />
    </div>

    </c:if>

        <h1>ユーザー登録</h1>

        <div class="form-touroku">

            <form action="UserTourokuServlet" method="post">
                <div class="form-group">
                    <label for="exampleInputLoginid">ログインID</label>
                    <input type="text" class="form-control" id="exampleInputLoginid" placeholder="LoginId" name="loginid">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">パスワード</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password1">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword2">パスワード(確認)</label>
                    <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password(Confirm)" name="password2">
                </div>
                <div class="form-group">
                    <label for="exampleInputName">ユーザー名</label>
                    <input type="text" class="form-control" id="exampleInputName" placeholder="Name" name="name">
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail">メールアドレス</label>
                    <input type="email" class="form-control" id="exampleInputEmail" placeholder="Email" name="email">
                </div>
                <div class="form-group">
                    <label for="exampleInputBirthday">生年月日</label>
                    <input type="date" class="form-control" id="exampleInputBirthday" placeholder="Birthday" name="birthdate">
                </div>
                <button type="submit" class="btn btn-primary">登録</button>
            </form>

        </div>

    </div>

</body>

</html>
