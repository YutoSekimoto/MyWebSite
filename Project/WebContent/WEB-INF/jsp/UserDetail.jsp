<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>ユーザー詳細</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/User/UserDetail.css">
</head>

<body>

	<jsp:include page="/baselayout/header.jsp" />

	<div class="wrapper">

		<h1>ユーザー詳細</h1>

		<div class="table-detail">

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

		<a href="UserListServlet" class="btn btn-primary">戻る</a>

	</div>

</body>

</html>
