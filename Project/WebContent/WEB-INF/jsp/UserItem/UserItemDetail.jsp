<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>ユーザー/商品詳細</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/UserItem/UserItemDetail.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>ユーザー商品詳細</h1>
		<br> <br>

		<div class="item-detail">

			<div class="float-left">
				<p>
					<img src="img/<c:out value="${userItem.file}" />">
				</p>

				<p>
					価格<br>
					<c:out value="${userItem.price}" />
					円<br>
				</p>
				<br> <a href="UserItemSearchListServlet"
					class="btn btn-primary">ユーザー商品検索リストへ</a>
			</div>

			<div class="float-right">
				<h4>
					<c:out value="${userItem.name}" />
				</h4>
				<P>
					<br> <strong>＜詳細＞</strong><br>
					<c:out value="${userItem.detail}" />
					<br> <br>
				</P>
			</div>

		</div>

	</div>

</body>

</html>
