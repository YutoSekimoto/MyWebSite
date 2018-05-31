<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>管理者/商品一覧</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Manager/ManagerItemSearchList.css">
</head>

<body>

	<jsp:include page="/baselayout/header.jsp" />

	<div class="wrapper">

		<c:if test="${eM != null}">

			<div class="alert alert-danger" role="alert">
				<c:out value="${eM}" />
			</div>

		</c:if>

		<h1>商品検索</h1>

		<div class="form-search">

			<form action="ManagerItemSearchListServlet" method="post">
				<div class="form-group">
					<label for="exampleInputName">商品名</label> <input type="text"
						class="form-control" id="exampleInputName" placeholder="Name"
						name="name">
				</div>
				<div class="form-group">
					<label for="exampleInputPrice">値段</label> <input type="number"
						class="form-control" id="exampleInputPrice" placeholder="Price"
						name="price">
				</div>
				<div class="form-group">
					<label for="exampleInputImage">画像ファイル</label> <input type="text"
						class="form-control" id="exampleInputImage" placeholder="Image"
						name="imagefile">
				</div>
				<input type="submit" class="btn btn-primary" value="検索"> <a
					href="ManagerItemTourokuServlet">新規登録</a>
			</form>

		</div>
		<br> <br> <br> <br>

		<div class="item-list">

			<h1>商品検索結果一覧</h1>

			<ul>

				<c:forEach var="item" items="${itemList}">

					<li><a
						href="ManagerItemDetailServlet?id=<c:out value="${item.id}" />"
						class="gazou"><img src="img/<c:out value="${item.file}" />"></a>
						<div class="title-price">
							タイトル<br> <a
								href="ManagerItemDetailServlet?id=<c:out value="${item.id}" />"
								class="title"><c:out value="${item.name}" /></a> <br> <br>
							価格<br>
							<c:out value="${item.price}" />
							円
						</div>
						<div class="link-operate">
							<br> <br> <a
								href="ManagerItemDeleteServlet?id=<c:out value="${item.id}" />"
								class="btn btn-danger">削除</a> <a
								href="ManagerItemDetailServlet?id=<c:out value="${item.id}" />"
								class="btn btn-primary">詳細</a> <a
								href="ManagerItemUpdateServlet?id=<c:out value="${item.id}" />"
								class="btn btn-success">更新</a> <br> <br>
						</div></li>

					<div class="clear"></div>
				</c:forEach>

			</ul>

		</div>

	</div>

</body>

</html>
