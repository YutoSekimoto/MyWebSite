<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>ユーザー/商品検索一覧</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/UserItem/UserItemSearchList.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<c:if test="${eM != null}">
			<div class="alert alert-danger" role="alert">
				<c:out value="${eM}" />
			</div>
		</c:if>

		<h1>ユーザー商品検索リスト</h1>
		<br> <br>

		<div class="form-search">
			<form action="UserItemSearchListServlet" method="post">
				<div class="form-group">
					<label for="exampleInputName">商品名</label> <input type="text" class="form-control" id="exampleInputName" placeholder="Name" name="name">
				</div>
				<div class="form-group">
					<label for="exampleInputPrice">値段</label> <input type="number" class="form-control" id="exampleInputPrice" placeholder="Price" name="price">
				</div>
				<div class="form-group">
					<label for="exampleInputImage">画像ファイル</label> <input type="text" class="form-control" id="exampleInputImage" placeholder="Image" name="imagefile">
				</div>
				<div class="form-group">
					<label for="exampleInputImage">カテゴリー</label><br> <select name="categoryId">
						<option value="">カテゴリー選択</option>
						<c:forEach var="category" items="${categoryList}">
							<option value="<c:out value="${category.id}" />"><c:out value="${category.name}" /></option>
						</c:forEach>
					</select>
				</div>
				<input type="submit" class="btn btn-primary" value="検索"> <a href="UserItemTourokuServlet">ユーザー商品新規登録</a>
			</form>
		</div>
		<br> <br> <br> <br>

		<div class="item-list">
			<h1>商品検索結果一覧</h1>
			<br> <br>
			<ul>
				<c:forEach var="userItem" items="${userItemList}">
					<li><a href="UserItemDetailServlet?id=<c:out value="${userItem.id}" />" class="gazou"><img src="img/<c:out value="${userItem.file}" />"></a>

						<div class="title-price">
							タイトル<br> <a href="UserItemDetailServlet?id=<c:out value="${userItem.id}" />" class="title"><c:out value="${userItem.name}" /></a> <br> <br> 価格<br>
							<c:out value="${userItem.price}" />
							円
						</div>

						<div class="link-operate">
							<br> <br> <a href="UserItemDeleteServlet?id=<c:out value="${userItem.id}" />" class="btn btn-danger">削除</a> <a href="UserItemDetailServlet?id=<c:out value="${userItem.id}" />"
								class="btn btn-primary">詳細</a> <a href="UserItemUpdateServlet?id=<c:out value="${userItem.id}" />" class="btn btn-success">更新</a> <br> <br>
						</div></li>
					<div class="clear"></div>
				</c:forEach>
			</ul>
		</div>

	</div>

</body>

</html>
