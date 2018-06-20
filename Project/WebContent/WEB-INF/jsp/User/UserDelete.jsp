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

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>ユーザー削除</h1>

		<c:choose>
			<c:when test="${eM != null}">
				<c:out value="${eM}" />
				<br>
				<a href="UserListServlet">ユーザーリストへ</a>
			</c:when>

			<c:otherwise>
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
					<p>
						<strong>表示のユーザー情報を削除してもよろしいですか</strong>
					</p>
					<form action="UserDeleteServlet" method="post">
						<input type="hidden" name="id" value="<c:out value="${userDetail.id}" />"> <a href="UserListServlet" class="btn btn-primary">キャンセル</a> <input type="submit" class="btn btn-primary"
							value="削除">
					</form>
				</div>
			</c:otherwise>

		</c:choose>

	</div>

</body>

</html>
