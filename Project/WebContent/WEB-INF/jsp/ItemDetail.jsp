<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>商品詳細</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Item/ItemDetail.css">
</head>

<body>

	<jsp:include page="/baselayout/header.jsp" />

	<div class="wrapper">

		<h1>商品詳細・カート追加</h1>

		<div class="item-detail">

			<div class="float-left">
				<p>
					<img src="img/<c:out value="${item.file}" />">
				</p>

				<div class="price-add">
					<p>
						価格<br>
						<c:out value="${item.price}" />
						円 <br> <br>
					</p>
					<form action="" method="post">
						<input type="hidden" name="id"
							value="<c:out value="${item.id}" />"> 個数：<input
							type="number" name="number" max="9" min="1" required> <input
							type="submit" value="カートに追加">
					</form>
					<br> <a href="ManagerItemSearchListServlet"
						class="btn btn-primary">商品リストへ</a>

				</div>
			</div>

			<div class="float-right">

				<h4>
					<c:out value="${item.name}" />
				</h4>
				<P>
					<br> <strong>＜詳細＞</strong> <br>
					<c:out value="${item.detail}" />
					<br> <br>
				</P>

			</div>

		</div>

	</div>

</body>

</html>
