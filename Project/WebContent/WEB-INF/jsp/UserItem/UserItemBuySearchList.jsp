<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>フリーマーケット商品検索</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/UserItem/UserItemBuySearchList.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<div class="search-info">
			<h1>フリーマーケット商品検索</h1>
			<br> <br>

			<form action="UserItemBuySearchListServlet" method="post">
				<select name="categoryId">
					<option value="">カテゴリー選択</option>
					<c:forEach var="category" items="${categoryList}">
						<option value="<c:out value="${category.id}" />"><c:out value="${category.name}" /></option>
					</c:forEach>
				</select>&nbsp;&nbsp;<input type="text" name="name" class="name"> <input type="submit" value="検索" class="submit"> &nbsp;&nbsp;&nbsp;&nbsp;<strong>価格</strong>： <input type="number"
					name="bottomPrice" class="price">円〜 <input type="number" name="topPrice" class="price">円
			</form>
		</div>

		<div class="item-list">
			<c:if test="${eM != null}">
				<div class="eM">
					<c:out value="${eM}" />
					<br> <a href="UserItemBuySearchListServlet">ユーザー商品購入検索リストへ</a>
				</div>
			</c:if>

			<ul>
				<c:forEach var="userItem" items="${userItemList}">
					<li><a href="UserItemBuyDetailServlet?id=<c:out value="${userItem.id}" />"><img src="img/<c:out value="${userItem.file}" />"></a> <br> <br> <a
						href="UserItemBuyDetailServlet?id=<c:out value="${userItem.id}" />"><c:out value="${userItem.name}" /></a> <br> 価格：<a href="UserItemBuyDetailServlet?id=<c:out value="${userItem.id}" />"><c:out
								value="${userItem.price}" />円</a></li>
				</c:forEach>
			</ul>
		</div>

	</div>

</body>

</html>
