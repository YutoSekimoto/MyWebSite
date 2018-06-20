<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>ストア商品カート一覧</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Item/ItemCartList.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>ストア商品カート一覧</h1>
		<br> <br>

		<div class="item-list">
			<ul>
				<c:forEach var="sItem" items="${sItemList}">
					<c:if test="${sItem.userId == usersession.id}">
						<li><a href="ItemDetailServlet?id=<c:out value="${sItem.id}" />" class="gazou"><img src="img/<c:out value="${sItem.file}" />"></a>
							<div class="title-price">
								<h5>
									<a href="ItemDetailServlet?id=<c:out value="${sItem.id}" />" class="title"><c:out value="${sItem.name}" /></a>
								</h5>
								<br> <br> 値段：
								<c:out value="${sItem.price}" />
								円 &nbsp;&nbsp;&nbsp;&nbsp; 個数：
								<form action="ItemCartListServlet" method="post" class="formNumber">
									<select name="number" id="number" required>
										<c:forEach var="i" begin="0" end="9" step="1">
											<c:choose>
												<c:when test="${i == sItem.number}">
													<option value="<c:out value="${i}" />" selected><c:out value="${i}" /></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${i}" />"><c:out value="${i}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select> <input type="hidden" name="itemId" value="<c:out value="${sItem.id}" />">
								</form>
							</div>

							<div class="form-operate">
								合計金額：
								<c:out value="${sItem.number * sItem.price}" />
								円 <br> <br>
								<form action="ItemCartListServlet" method="post">
									<input type="hidden" name="id" value="<c:out value="${sItem.id}" />"> <input type="submit" value="削除する" name="delete">
								</form>
								<br>
							</div></li>
						<div class="clear"></div>
					</c:if>
				</c:forEach>
				<li>

					<div class="space">
						<form action="ItemCartListServlet" method="post" id="formDelivery">
							<select name="deliveryName" id="delivery-name" required>
								<option selected>配送方法選択</option>
								<option value="通常配送">通常配送</option>
								<option value="お急ぎ便">お急ぎ便</option>
								<option value="お届け日時指定便">お届け日時指定便</option>
							</select>
						</form>
					</div>

					<div class="title-price">
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

					<div class="form-operate">
						<c:forEach var="delivery" items="${deliveryList}">
							<c:if test="${delivery != null && delivery.getUserId() == usersession.getId()}">
                             合計金額：<c:out value="${delivery.price}" />円
                            </c:if>
						</c:forEach>
					</div>
				</li>
			</ul>
			<div class="clear"></div>

			<div class="buy">
				<c:choose>
					<c:when test="${sItemList != null && !sItemList.isEmpty() && deliveryList != null}">
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
						<form action="ItemBuyConfirmServlet" method="get">
							<input type="submit" value="購入確認へ">
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

			<a href="ItemListServlet" class="a-itemlist"> ・ストア商品一覧へ</a><br>
		</div>

	</div>

	<script>
		document.addEventListener("DOMContentLoaded", function() {

			//デリバリー名が変更した場合
			document.getElementById("formDelivery").addEventListener("change" , function() {

						this.submit();

			}, false)

			//個数一覧を取得
			var list = document.getElementsByClassName("formNumber");

			for (var i = 0, len = list.length; i < len; i++) {

				//個数の値が変更した場合
				list.item(i).addEventListener("change" , function() {

					this.submit();

				}, false)
			}

		}, false)
	</script>
	<noscript>JavaScriptは利用できません</noscript>
</body>

</html>
