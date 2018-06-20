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

		<h1>ユーザー更新</h1>
		<br> <br>

		<c:choose>
			<c:when test="${rM != null}">
				<c:out value="${rM}"></c:out>
				<br>
				<a href="UserListServlet">戻る</a>
			</c:when>

			<c:otherwise>
				<div class="loginid">
					<h4>
						ログインID&nbsp;&nbsp;&nbsp;&nbsp;
						<c:out value="${userDetail.loginId}" />
					</h4>
				</div>

				<div class="form-update">
					<form action="UserUpdateServlet" method="post">
						<div class="form-group">
							<label for="exampleInputPassword1">パスワード</label> <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password1" required>
						</div>
						<div class="form-group">
							<label for="exampleInputPassword2">パスワード(確認)</label> <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password(confirm)" name="password2" required>
						</div>
						<div class="form-group">
							<label for="exampleInputName">ユーザー名</label> <input type="text" class="form-control" id="exampleInputName" placeholder="Name" name="name" value="<c:out value="${userDetail.name}" />" required>
						</div>
						<div class="form-group">
							<label for="exampleInputAddress">住所</label> <input type="text" class="form-control" id="exampleInputAddress" placeholder="Address" name="address" required
								value="<c:out value="${userDetail.address}" />">
						</div>
						<div class="form-group">
							<label for="exampleInputBirthday">生年月日</label> <input type="date" class="form-control" id="exampleInputBirthday" placeholder="Birthday" name="birthDate"
								value="<c:out value="${userDetail.birthDate}" />" required>
						</div>
						<input type="hidden" name="id" value="<c:out value="${userDetail.id}" />"> <a href="UserListServlet" class="btn btn-primary">キャンセル</a> <input type="submit" class="btn btn-primary"
							value="更新" id="update">
					</form>
				</div>
			</c:otherwise>
		</c:choose>

	</div>

	<script>

    document.addEventListener("DOMContentLoaded" , function(){

        document.getElementById("update").addEventListener("click" ,function(e){

        	    //パスワードとパスワード（確認）を取得
            var password1 = document.getElementsByName("password1");
            var password2 = document.getElementsByName("password2");

            //パスワードが異なればイベントをキャンセル
            if(password1.item(0).value != password2.item(0).value){

            	//イベントをキャンセル
            e.preventDefault();

            	//アラートを設定・表示
			var message = document.getElementById("message");
			message.className = "alert alert-danger";
			message.role = "alert";

			var alert = document.getElementsByClassName("alert alert-danger");
			alert.item(0).textContent = "パスワードが一致しません";

            }

        } , false)

    } , false)

    </script>
	<noscript>JavaScriptが利用できません</noscript>
</body>

</html>
