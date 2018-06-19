<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>ログイン</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Other/login.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<c:if test="${eM != null}">
			<div class="alert alert-danger" role="alert">
				<c:out value="${eM}" />
			</div>
		</c:if>

		<h1>ログイン</h1>

		<div class="form-login">

			<form action="LoginServlet" method="post">
				<div class="form-group">
					<label for="exampleInputLoginid">ログインID</label> <input type="text"
						class="form-control" id="exampleInputLoginid"
						placeholder="LoginId" name="loginid" required>
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">パスワード</label> <input
						type="password" class="form-control" id="exampleInputPassword1"
						placeholder="Password" name="password" required>
				</div>
				<input type="submit" class="btn btn-primary" value="ログイン" id="login">
			</form>

		</div>

	</div>

</body>

</html>
