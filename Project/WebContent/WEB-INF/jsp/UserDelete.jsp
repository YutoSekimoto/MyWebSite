<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>ユーザー削除</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/User/UserDelete.css">
</head>

<body>

    <div class="wrapper">

        <h1>ユーザー削除</h1>

        <div class="delete">

            <table class="table">
                <tr>
                    <th scope="row">ログインID</th>
                    <td><c:out value="${userDetail.loginId}" /></td>
                </tr>
                <tr>
                    <th scope="row">ユーザー名</th>
                    <td><c:out value="${userDetail.name}" /></td>
                </tr>
                <tr>
                    <th scope="row">メールアドレス</th>
                    <td><c:out value="${userDetail.email}" /></td>
                </tr>
                <tr>
                    <th scope="row">生年月日</th>
                    <td><c:out value="${userDetail.birthDate}" /></td>
                </tr>
                <tr>
                    <th scope="row">登録日時</th>
                    <td><c:out value="${userDetail.createDate}" /></td>
                </tr>
            </table>

        </div>

        <div class="form-login">

            <p>表示のユーザー情報を削除してもよろしいですか？</p>

            <form action="UserDeleteServlet" method="post">
                <input type="hidden" name="id" value="<c:out value="${userDetail.id}" />">
                <button type="submit" class="btn btn-primary">キャンセル</button>
                <button type="submit" class="btn btn-primary">削除</button>
            </form>

        </div>

    </div>

</body>

</html>
