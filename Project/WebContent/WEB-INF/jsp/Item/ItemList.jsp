<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>ストア商品検索</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Item/ItemList.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<div class="search-info">

			<h1>ストア商品検索</h1>
			<br> <br>

			<form action="ItemListServlet" method="post">
				<input type="text" name="name" class="name"> <input
					type="submit" value="検索" class="submit">
				&nbsp;&nbsp;&nbsp;&nbsp;<strong>価格</strong>： <input type="number"
					name="bottomPrice" class="price">円〜 <input type="number"
					name="topPrice" class="price">円
			</form>

		</div>

		<div class="item-list">

			<c:if test="${eM != null}">
				<div class="eM">
					<c:out value="${eM}" />
					<br> <a href="ItemListServlet">ストア商品検索へ</a>
				</div>
			</c:if>

			<ul>

				<c:forEach var="item" items="${itemList}">
					<li><a
						href="ItemDetailServlet?id=<c:out value="${item.id}" />"><img
							src="img/<c:out value="${item.file}" />"></a> <br> <br>
						<a href="ItemDetailServlet?id=<c:out value="${item.id}" />"><c:out
								value="${item.name}" /></a> <br> 価格：<a
						href="ItemDetailServlet?id=<c:out value="${item.id}" />"><c:out
								value="${item.price}" />円</a></li>
				</c:forEach>

			</ul>

		</div>

	</div>

</body>

</html>
