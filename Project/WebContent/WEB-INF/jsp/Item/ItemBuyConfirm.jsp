<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="beans.ItemBeans"%>
<%@ page import="beans.DeliveryBeans"%>
<%@ page import="beans.UserBeans"%>
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

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>商品購入確認</h1>
		<br> <br>

		<div class="item-list">

			<ul>

				<c:forEach var="sItem" items="${sItemList}">

					<c:if test="${sItem.userId == usersession.id}">

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

					</c:if>

				</c:forEach>

				<li>
					<div class="delivery-name">

						配送方法：

						<c:choose>

							<c:when test="${deliveryList == null}">未設定</c:when>

							<c:when test="${deliveryList != null}">

								<%
								//セッションスコープを取得
									ArrayList<DeliveryBeans> jspDeliveryList = (ArrayList<DeliveryBeans>) session.getAttribute("deliveryList");
											UserBeans jspUser = (UserBeans) session.getAttribute("usersession");

											//カートの配達変数を設定
											String deliveryName = "未設定";

											//カートの配達を設定
											for (DeliveryBeans jspDelivery : jspDeliveryList) {

												//ユーザーによって振り分け
												if (jspDelivery.getUserId() == jspUser.getId()) {
													deliveryName = jspDelivery.getName();
													break;
												}

											}
								%>

								<%=deliveryName%>

							</c:when>

						</c:choose>
					</div>

					<div class="delivery-price">

						<c:forEach var="delivery" items="${deliveryList}">
							<c:if
								test="${delivery != null && delivery.getUserId() == usersession.getId()}">
                             合計金額：<c:out value="${delivery.price}" />円
                            </c:if>
						</c:forEach>

					</div>

				</li>

			</ul>

			<div class="clear"></div>

			<div class="buy-confirm">

				<c:choose>

					<c:when
						test="${sItemList != null && !sItemList.isEmpty() && deliveryList != null}">

						<%
						//セッションスコープを取得
							ArrayList<ItemBeans> jspItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");
									ArrayList<DeliveryBeans> jspDeliveryList = (ArrayList<DeliveryBeans>) session.getAttribute("deliveryList");
									UserBeans jspUser = (UserBeans) session.getAttribute("usersession");

									//カートの合計価格・配達・合計価格表示文字列変数を設定
									int totalPrice = 0;
									String totalPriceMessage = null;
									String deliveryName = null;

									//カートの配達価格・配達名を設定
									for (DeliveryBeans jspDelivery : jspDeliveryList) {

										//ユーザーによって振り分け
										if (jspDelivery.getUserId() == jspUser.getId()) {
											totalPrice = jspDelivery.getPrice();
											deliveryName = jspDelivery.getName();
										}

									}

									//配送方法が未設定の場合
									if (totalPrice == 0 && deliveryName == null) {

										totalPriceMessage = "配送方法が未選択です";

									} else {

										//カートの合計価格を設定
										for (ItemBeans jspItem : jspItemList) {

											//ユーザーによって振り分け
											if (jspItem.getUserId() == jspUser.getId()) {
												totalPrice += jspItem.getPrice() * jspItem.getNumber();
											}
										}

										//合計価格表示文字列を設定
										totalPriceMessage = "合計金額：" + totalPrice + "円";

									}
						%>

						<%
						//合計価格表示文字列が設定されていた場合
							if (totalPriceMessage != null) {
						%>

						<%=totalPriceMessage%>
						<br>
						<br>
						<form action="ItemBuyConfirmServlet" method="post">
							<input type="submit" value="購入する">
						</form>

						<%
							}
						%>

					</c:when>

					<c:when test="${sItemList == null || sItemList.isEmpty()}">
            カートに商品がありません
            </c:when>

					<c:when test="${deliveryList == null}">
            配送方法が未選択です
            </c:when>

				</c:choose>

			</div>

		</div>

	</div>

</body>

</html>
