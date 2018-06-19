<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>管理者/商品削除</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Manager/ManagerItemDelete.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>管理者商品削除</h1>
		<br> <br>

		<c:choose>

			<c:when test="${eM != null}">
				<div class="item-result">
					<c:out value="${eM}" />
					<br> <a href="ManagerItemSearchListServlet">商品管理者リストへ</a>
				</div>
			</c:when>

			<c:otherwise>

				<div class="item-detail">

					<div class="float-left">

						<p>
							<img src="img/<c:out value="${item.file}" />">
						</p>

						<p>
							価格<br>
							<c:out value="${item.price}" />
							円
						</p>
						<br>

						<p>
							<strong>この商品を削除してもよろしいですか</strong>
						</p>

						<form action="ManagerItemDeleteServlet" method="post">
							<input type="hidden" name="id"
								value="<c:out value="${item.id}" />"> <a
								href="ManagerItemSearchListServlet" class="btn btn-primary">キャンセル</a>
							<input type="submit" class="btn btn-primary" value="削除する">
						</form>

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

			</c:otherwise>

		</c:choose>

	</div>

</body>

</html>
