<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>商品購入履歴リスト</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Item/ItemBuyHistoryList.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>商品購入履歴リスト</h1>
		<br> <br>

		<div class="item-history-list">

			<h2>ストア購入履歴</h2>
			<br> <br>

			<table class="table">

				<thead>
					<tr>
						<th scope="col">配送方法</th>
						<th scope="col">注文合計金額</th>
						<th scope="col">注文日時</th>
						<th scope="col"></th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="buy" items="${buyList}">
						<tr>
							<td><c:out value="${buy.deliveryMethod}" /></td>
							<td><c:out value="${buy.price}" />円</td>
							<td><c:out value="${buy.formatDate}" /></td>
							<td><a
								href="ItemBuyHistoryDetailServlet?id=<c:out value="${buy.id}" />">詳細</a></td>
						</tr>
					</c:forEach>
				</tbody>

			</table>

		</div>

		<div class="user-item-history-list">

			<h2>フリーマーケット購入履歴</h2>
			<br> <br>

			<table class="table">

				<thead>
					<tr>
						<th scope="col">画像</th>
						<th scope="col">タイトル</th>
						<th scope="col">価格</th>
						<th scope="col">注文日時</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="userBuy" items="${userBuyList}">
						<tr>

							<td class="img-table"><img
								src="img/<c:out value="${userBuy.imageFile}" />"></td>
							<td><c:out value="${userBuy.itemName}" /></td>
							<td><c:out value="${userBuy.price}" />円</td>
							<td><c:out value="${userBuy.formatDate}" /></td>
						</tr>
					</c:forEach>
				</tbody>

			</table>

		</div>

	</div>

</body>

</html>