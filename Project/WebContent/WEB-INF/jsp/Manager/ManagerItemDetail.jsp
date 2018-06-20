<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>管理者/商品詳細</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Manager/ManagerItemDetail.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>管理者商品詳細</h1>
		<br> <br>

		<div class="item-detail">
			<div class="float-left">
				<p>
					<img src="img/<c:out value="${item.file}" />">
				</p>
				<p>
					価格<br>
					<c:out value="${item.price}" />
					円<br>
				</p>
				<br> <a href="ManagerItemSearchListServlet" class="btn btn-primary">管理者商品リストへ</a>
			</div>

			<div class="float-right">
				<h4>
					<c:out value="${item.name}" />
				</h4>
				<P>
					<br> <strong>＜詳細＞</strong><br>
					<c:out value="${item.detail}" />
					<br> <br>
				</P>
			</div>
		</div>

	</div>

</body>

</html>
