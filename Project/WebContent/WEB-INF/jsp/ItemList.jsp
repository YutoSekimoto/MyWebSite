<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>商品検索</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/Item/ItemList.css">
</head>

<body>

    <div class="wrapper">

        <div class="search-info">

            ◯◯◯の検索結果…

        </div>
        <br>

        <div class="item-list">

            <ul>

            <c:forEach var="item" items="${itemList}">
                <li>
                    <a href="ItemDetailServlet?id=<c:out value="${item.id}" />"><img src="img/<c:out value="${item.file}" />"></a>
                    <br><br>
                    <a href="ItemDetailServlet?id=<c:out value="${item.id}" />"><c:out value="${item.name}" /></a>
                    <br>
                    価格：<a href="ItemDetailServlet?id=<c:out value="${item.id}" />"><c:out value="${item.price}" />円</a>
                </li>
            </c:forEach>

            </ul>

        </div>

    </div>

</body>

</html>
