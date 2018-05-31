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
<title>商品カート</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Item/ItemCartList.css">
</head>

<body>

	<div class="wrapper">

		<h1>商品カート一覧</h1>
		<br>
		<br>

		<div class="item-list">

			<ul>

				<c:forEach var="sItem" items="${sItemList}">

					<li><a
						href="ItemDetailServlet?id=<c:out value="${sItem.id}" />"
						class="gazou"><img src="img/<c:out value="${sItem.file}" />"></a>

						<div class="title-price">
							<a href="ItemDetailServlet?id=<c:out value="${sItem.id}" />"
								class="title"><c:out value="${sItem.name}" /></a> <br>
							<br> 値段：
							<c:out value="${sItem.price}" />
							&nbsp;&nbsp;&nbsp;&nbsp; 個数：
							<form action="ItemCartListServlet" method="post"
								class="formNumber">
								<select name="number" id="number" required>

									<c:forEach var="i" begin="0" end="9" step="1">
										<c:choose>
											<c:when test="${i == sItem.number}">
												<option value="<c:out value="${i}" />" selected><c:out
														value="${i}" /></option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${i}" />"><c:out
														value="${i}" /></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>

								</select> <input type="hidden" name="itemId"
									value="<c:out value="${sItem.id}" />">

							</form>

						</div>

						<div class="form-operate">
							合計金額：
							<c:out value="${sItem.number * sItem.price}" />
							<br>
							<br>
							<form action="ItemCartListServlet" method="post">
								<input type="hidden" name="id"
									value="<c:out value="${sItem.id}" />"> <input
									type="submit" value="削除する" name="delete">
							</form>
							<br>
						</div></li>

					<div class="clear"></div>

				</c:forEach>

				<li>

					<div class="space">
						<form action="ItemCartListServlet" method="post">
							<select name="deliveryName" id="delivery-name" required>
								<option selected>配送方法選択</option>
								<option value="通常配送">通常配送</option>
								<option value="お急ぎ便">お急ぎ便</option>
								<option value="お届け日時指定便">お届け日時指定便</option>
							</select><br> <br> <input type="submit" name="delivery"
								id="delivery" value="配送方法決定">
						</form>
					</div>

					<div class="title-price">
						配送方法：
						<c:out value="${delivery.name}" />
						<c:if test="${delivery == null }">未決定</c:if>
						<br> <br>
						<c:if test="${delivery != null }">
                            値段：<c:out value="${delivery.price}" />円
                            </c:if>
					</div>

					<div class="form-operate">
						<c:if test="${delivery != null }">
                             合計金額：<c:out value="${delivery.price}" />円
                            </c:if>
					</div>

				</li>

			</ul>

			<div class="clear"></div>

			<div class="buy">

				<c:choose>

					<c:when
						test="${sItemList != null && !sItemList.isEmpty() && delivery != null}">

            合計金額：
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
            <br>
						<br>

						<form action="ItemBuyConfirm" method="get">
							<input type="submit" value="購入する">
						</form>

					</c:when>

					<c:when test="${sItemList == null || sItemList.isEmpty()}">
            カートに商品がありません
            </c:when>

					<c:when test="${delivery == null}">
            配送方法が未選択です
            </c:when>
				</c:choose>

			</div>

			<a href="ItemListServlet">・商品一覧へ</a><br>

		</div>

	</div>

	<script>
		document.addEventListener("DOMContentLoaded", function() {

			document.getElementById("delivery-name")
					.addEventListener(
							"change",
							function() {

								var delivery = document
										.getElementById("delivery-name").value;
								document.getElementById("delivery").click();

							}, false)

			var list = document.getElementsByClassName("formNumber");

			for (var i = 0, len = list.length; i < len; i++) {

				list.item(i).addEventListener("change", function() {

					this.submit();

				}, false)
			}

		}, false)
	</script>
	<noscript>JavaScriptは利用できません</noscript>
</body>
</body>

</html>
