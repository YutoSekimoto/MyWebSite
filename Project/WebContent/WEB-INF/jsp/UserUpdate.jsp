<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>ユーザー更新</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/User/UserUpdate.css">
</head>

<body>

    <div class="wrapper">

    <c:if test="${eM != null}">

    <div class="alert alert-danger" role="alert">
    <c:out value="${eM}" />
    </div>

    </c:if>

        <h1>ユーザー更新</h1>
        <br><br>

        <div class="loginid">

            <h4>ログインID&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${userDetail.loginId}" /></h4>

        </div>

        <div class="form-update">

            <form action="" method="post">
                <div class="form-group">
                    <label for="exampleInputPassword1">パスワード</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password1">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword2">パスワード(確認)</label>
                    <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password(confirm)" name="password2">
                </div>
                <div class="form-group">
                    <label for="exampleInputName">ユーザー名</label>
                    <input type="text" class="form-control" id="exampleInputName" placeholder="Name" name="name" value="<c:out value="${userDetail.name}" />">
                </div>
                <div class="form-group">
                    <label for="exampleInputBirthday">生年月日</label>
                    <input type="date" class="form-control" id="exampleInputBirthday" placeholder="Birthday" name="birthDate" value="<c:out value="${userDetail.birthDate}" />">
                </div>
                <input type="hidden" name="id" value="<c:out value="${userDetail.id}" />">
                <button type="submit" class="btn btn-primary">更新</button>
            </form>

        </div>

    </div>

</body>

</html>
