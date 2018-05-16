<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <div class="wrapper">

        <h1>商品購入確認リスト</h1><br><br>

        <div class="item-history-list">

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
                        <td><c:out value="${buy.createDate}" /></td>
                        <td><a href="ItemBuyHistoryDetailServlet?id=<c:out value="${buy.id}" />">詳細</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>

    </div>

</body>

</html>