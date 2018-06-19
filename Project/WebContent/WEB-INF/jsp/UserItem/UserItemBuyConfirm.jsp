<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>フリーマーケット商品購入確認</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/UserItem/UserItemBuyConfirm.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>フリーマーケット商品購入確認</h1>
		<br> <br>

		<div class="item-list">

			<c:if test="${userItem != null}">
				<img src="img/<c:out value="${userItem.file}" />">
				<div class="title-price">
					タイトル：
					<c:out value="${userItem.name}" />
					<br> <br> 価格：
					<c:out value="${userItem.price}" />
				</div>
				<div class="clear"></div>
			</c:if>

			<div class="buy-confirm">

				<c:choose>

					<c:when test="${userItem != null}">

						<h4>
							購入合計金額：
							<c:out value="${userItem.price}" />
							円
						</h4>
						<br>

						<form action="UserItemBuyConfirmServlet" method="post">
							<input type="hidden" name="id"
								value="<c:out value="${userItem.id}" />"> <input
								type="submit" value="購入する" name="buy">&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="UserItemBuySearchListServlet">フリーマーケット商品検索へ</a>
						</form>
					</c:when>

					<c:otherwise>
						<a href="UserItemBuySearchListServlet" type="button">フリーマーケット商品検索へ</a>
					</c:otherwise>

				</c:choose>

			</div>

		</div>

	</div>

</body>

</html>
