<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="beans.ItemBeans" %>
<%@ page import="beans.DeliveryBeans" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

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

    <h1>商品カート一覧</h1><br><br>

        <div class="item-list">

            <ul>
            <c:forEach var="sItem" items="${sItemList}">
                <div class="li">
                    <li>
                        <a href="" class="gazou"><img src="img/<c:out value="${sItem.file}" />"></a>
                        <div class="title-price">
                            <a href="" class="title"><c:out value="${sItem.name}" /></a>
                            <br><br>
                            値段：<c:out value="${sItem.price}" />&nbsp;&nbsp;&nbsp;&nbsp;個数：<c:out value="${sItem.number}" />
                            <br><br>
                            <a href="" class="price">詳細チェック</a>
                        </div>
                        <div class="form-operate">
                            合計金額：<c:out value="${sItem.number * sItem.price}" />
                            <br><br>
                            <form action="ItemCartListServlet" method="post">
                                <input type="hidden" name="id" value="<c:out value="${sItem.id}" />">
                                <input type="submit" value="削除する" name="delete">
                            </form>
                            <br>
                        </div>
                    </li>
                </div>
                <div class="clear"></div>
            </c:forEach>

            <div class="li">
                    <li>
                        <div class="space">
                            <form action="ItemCartListServlet" method="post">
                                <select name="deliveryName" required>
                                    <option value="通常配送">通常配送</option>
                                    <option value="お急ぎ便">お急ぎ便</option>
                                    <option value="お届け日時指定便">お届け日時指定便</option>
                                </select><br><br>
                                <input type="submit" name="delivery" value="配送方法決定">
                            </form>
                        </div>
                        <div class="title-price">
                            配送方法：<c:out value="${delivery.name}" />
                            <br><br>
                            値段：<c:out value="${delivery.price}" />円
                        </div>
                        <div class="form-operate">
                            合計金額：<c:out value="${delivery.price}" />円
                        </div>
                    </li>
                </div>
                <div class="clear"></div>

            </ul>

            <div class="buy">

            <c:choose>
            <c:when test="${sItemList != null && !sItemList.isEmpty() && delivery != null}">

            合計金額：
            <%
            ArrayList<ItemBeans> jspItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");
            DeliveryBeans jspDelivery = (DeliveryBeans) session.getAttribute("delivery");

            int totalPrice = jspDelivery.getPrice();

            for(ItemBeans jspItem : jspItemList){
            totalPrice += jspItem.getPrice() * jspItem.getNumber();
            }
            out.println(totalPrice);
            %>
            円
            <br><br>
            <form action="ItemBuyConfirm" method="get">
                <input type="submit" value="購入する" name="buy">
            </form>
            </c:when>

            <c:when test="${sItemList == null || sItemList.isEmpty()}">
            カートに商品を追加してください
            </c:when>

            <c:when test="${delivery == null}">
            配送方法が未選択です
            </c:when>
            </c:choose>

            </div>

            <a href="" onclick="history.go(-2); return false;">・戻る</a><br>
            <a href="ItemListServlet">・商品一覧へ</a><br>

        </div>

    </div>

</body>

</html>
