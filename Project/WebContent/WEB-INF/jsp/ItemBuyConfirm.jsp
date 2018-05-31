<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="beans.ItemBeans"%>
<%@ page import="beans.DeliveryBeans"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>商品購入確認</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Item/ItemBuyConfirm.css">
</head>

<body>

	<jsp:include page="/baselayout/header.jsp" />

	<div class="wrapper">

		<h1>商品購入確認</h1>
		<br> <br>

		<div class="item-list">

			<ul>
				<c:forEach var="sItem" items="${sItemList}">
					<li><img src="img/<c:out value="${sItem.file}" />">
						<div class="title-price">
							<c:out value="${sItem.name}" />
							<br> <br> 価格：
							<c:out value="${sItem.price}" />
							&nbsp;&nbsp;&nbsp;&nbsp;個数：
							<c:out value="${sItem.number}" />
							<br> <br> 合計価格：
							<c:out value="${sItem.price * sItem.number}" />
							円
						</div></li>
					<div class="clear"></div>
				</c:forEach>

				<li>
					<div class="delivery-name">
						配送方法：
						<c:out value="${delivery.name}" />
					</div>
					<div class="delivery-price">
						価格：
						<c:out value="${delivery.price}" />
						円
					</div>
				</li>
			</ul>

			<div class="clear"></div>

			<div class="buy-confirm">

				<c:choose>
					<c:when
						test="${sItemList != null && !sItemList.isEmpty() && delivery != null}">

						<h4>
							購入合計金額：
							<%
							ArrayList<ItemBeans> jspItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");
									DeliveryBeans jspDelivery = (DeliveryBeans) session.getAttribute("delivery");

									int totalPrice = jspDelivery.getPrice();

									for (ItemBeans jspItem : jspItemList) {
										totalPrice += jspItem.getPrice() * jspItem.getNumber();
									}
									out.println(totalPrice);
						%>
							円
						</h4>
						<br>

						<form action="ItemBuyConfirm" method="post">
							<input type="submit" value="購入する" name="buy">&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="ItemCartListServlet">商品カートへ</a>
						</form>
					</c:when>
					<c:otherwise>
						<meta http-equiv="refresh" content="0; URL='ItemListServlet'" />
						<a href="ItemListServlet" type="button">商品カートへ</a>
					</c:otherwise>

				</c:choose>

			</div>

		</div>

	</div>

</body>

</html>
