<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<c:choose>
			<c:when test="${eM != null}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${eM}" />
					<div id="message"></div>
				</div>
			</c:when>
			<c:otherwise>
				<div id="message"></div>
			</c:otherwise>
		</c:choose>

		<h1>ユーザー新規登録</h1>

		<div class="form-touroku">

			<form action="UserTourokuServlet" method="post">
				<div class="form-group">
					<label for="exampleInputLoginid">ログインID</label> <input type="text"
						class="form-control" id="exampleInputLoginid"
						placeholder="LoginId" name="loginid" required>
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">パスワード</label> <input
						type="password" class="form-control" id="exampleInputPassword1"
						placeholder="Password" name="password1" required>
				</div>
				<div class="form-group">
					<label for="exampleInputPassword2">パスワード(確認)</label> <input
						type="password" class="form-control" id="exampleInputPassword2"
						placeholder="Password(Confirm)" name="password2" required>
				</div>
				<div class="form-group">
					<label for="exampleInputName">ユーザー名</label> <input type="text"
						class="form-control" id="exampleInputName" placeholder="Name"
						name="name" required>
				</div>
				<div class="form-group">
					<label for="exampleInputEmail">メールアドレス</label> <input type="email"
						class="form-control" id="exampleInputEmail" placeholder="Email"
						name="email" required>
				</div>
				<div class="form-group">
					<label for="exampleInputAddress">住所</label> <input type="text"
						class="form-control" id="exampleInputAddress"
						placeholder="Address" name="address" required>
				</div>
				<div class="form-group">
					<label for="exampleInputBirthday">生年月日</label> <input type="date"
						class="form-control" id="exampleInputBirthday"
						placeholder="Birthday" name="birthdate" required>
				</div>
				<a href="UserListServlet" class="btn btn-primary">キャンセル</a> <input
					type="submit" class="btn btn-primary" value="登録" id="touroku">
			</form>

		</div>

	</div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {

            document.getElementById("touroku").addEventListener("click", function(e) {

            	//パスワードとパスワード（確認）を取得
                var password1 = document.getElementsByName("password1");
                var password2 = document.getElementsByName("password2");

              //パスワードが異なればイベントをキャンセル
                if (password1.item(0).value !== password2.item(0).value) {

                	//イベントをキャンセル
                    e.preventDefault();

                  //アラートを設定・表示
                    var message = document.getElementById("message");
                    message.className = "alert alert-danger";
                    message.role = "alert";

                    var alert = document.getElementsByClassName("alert alert-danger");
                    alert.item(0).textContent = "パスワードが一致しません";

                }

            }, false)
        }, false)
    </script>
	<noscript>JavaScriptが利用できません</noscript>
</body>

</html>
