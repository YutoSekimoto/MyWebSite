<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>商品購入確認</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Item/ItemBuyHistoryDetail.css">
</head>

<body>

	<jsp:include page="/baselayout/header.jsp" />

	<div class="wrapper">

		<h1>商品購入確認</h1>
		<br> <br>

		<div class="item-history-list">

			<table class="table">
				<thead>
					<tr>
						<th scope="col">配送方法</th>
						<th scope="col">注文合計金額</th>
						<th scope="col">注文日時</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><c:out value="${buy.deliveryMethod}" /></td>
						<td><c:out value="${buy.price}" />円</td>
						<td><c:out value="${buy.createDate}" /></td>
					</tr>
				</tbody>
			</table>

		</div>

		<div class="item-list">

			<ul>

				<c:forEach var="item" items="${itemList}">
					<li><img src="img/<c:out value="${item.file}" />">
						<div class="title-price">
							タイトル：
							<c:out value="${item.name}" />
							<br> <br> 価格：
							<c:out value="${item.price}" />
							&nbsp;&nbsp;&nbsp;&nbsp;個数：
							<c:out value="${item.number}" />
							<br> <br> 合計価格：
							<c:out value="${item.price * item.number}" />
							円
						</div></li>
					<div class="clear"></div>
				</c:forEach>

				<li>
					<div class="delivery-name">
						配送方法：
						<c:out value="${buy.deliveryMethod}" />
					</div>
					<div class="delivery-price">
						価格：
						<c:out value="${buy.deliveryPrice}" />
						円
					</div>
				</li>

			</ul>
			<div class="clear"></div>

		</div>

	</div>

</body>

</html>