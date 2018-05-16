<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>ユーザー検索</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/User/UserList.css">
</head>

<body>

    <div class="wrapper">

    <c:out value="${usersession.name }" />

        <h1>ユーザー検索</h1>

        <div class="form-search">

            <form action="UserListServlet" method="post">
                <div class="form-group">
                    <label for="exampleInputLoginid">ログインID</label>
                    <input type="text" class="form-control" id="exampleInputLoginid" placeholder="LoginId" name="loginId">
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
                    <input type="text" class="form-control" id="exampleInputBirthday" placeholder="Birthday" name="birthDate">
                </div>
                <button type="submit" class="btn btn-secondary">検索</button>
            </form>

        </div>
        <br><br><br><br>

        <div class="table-list">

            <h1>ユーザー検索結果一覧</h1>

            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">ログインID</th>
                        <th scope="col">ユーザー名</th>
                        <th scope="col">メールアドレス</th>
                        <th scope="col">生年月日</th>
                        <th scope="col">設定</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="oneUser" items="${userList}">
                    <tr>
                        <td><c:out value="${oneUser.loginId }" /></td>
                        <td><c:out value="${oneUser.name }" /></td>
                        <td><c:out value="${oneUser.email }" /></td>
                        <td><c:out value="${oneUser.birthDate }" /></td>
                        <td>
                            <a href="UserDeleteServlet?id=<c:out value="${oneUser.id }" />" class="btn btn-danger">削除</a>
                            <a href="UserDetailServlet?id=<c:out value="${oneUser.id }" />" class="btn btn-primary">詳細</a>
                            <a href="UserUpdateServlet?id=<c:out value="${oneUser.id }" />" class="btn btn-success">更新</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>

        <div class="link-action">

            <a href="UserTourokuServlet" class="btn btn-link">新規登録</a>
            <a href="LogoutServlet" class="btn btn-link">ログアウト</a>

        </div>

    </div>

</body>

</html>
