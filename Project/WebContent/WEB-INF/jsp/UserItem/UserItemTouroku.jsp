<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>ユーザー/商品登録</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/UserItem/UserItemTouroku.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<c:if test="${eM != null}">

			<div class="eM">
				☆
				<c:out value="${eM}" />
				☆ <br> <a href="UserItemSearchListServlet">ユーザー商品検索リストへ</a>
			</div>

		</c:if>

		<h1>ユーザー商品登録</h1>
		<br> <br>

		<div class="form-search">

			<form action="UserItemTourokuServlet" method="post"
				enctype="multipart/form-data">
				<div class="form-group">
					<label for="exampleInputName">商品名</label> <input type="text"
						class="form-control" id="exampleInputName" placeholder="Name"
						name="name" required>
				</div>
				<div class="form-group">
					<label for="exampleInputDetail">詳細</label>
					<textarea name="detail" class="form-control" rows="10"
						id="exampleInputDetail" placeholder="Detail" name="detail"
						required></textarea>
				</div>
				<div class="form-group">
					<label for="exampleInputPrice">値段</label> <input type="number"
						class="form-control" id="exampleInputPrice" placeholder="Price"
						name="price" required>
				</div>
				<div class="form-group">
					<label for="exampleInputImage">画像ファイル</label> <input type="file"
						class="form-control" id="exampleInputImage" placeholder="Image"
						name="imageFile" required>
				</div>
				<div class="form-group">
					<label for="exampleInputImage">カテゴリー</label><br> <select
						name="categoryId" required>
						<c:forEach var="category" items="${categoryList}">
							<option value="<c:out value="${category.id}" />"><c:out
									value="${category.name}" /></option>
						</c:forEach>
					</select>
				</div>

				<a href="UserItemSearchListServlet" class="btn btn-primary">キャンセル</a>
				<input type="submit" class="btn btn-primary" id="touroku" value="登録">
			</form>

		</div>
		<br> <br> <br> <br>

		<c:choose>

			<c:when test="${item != null}">

				<div class="item-detail">

					<h1>商品登録結果</h1>

					<div class="float-left">
						<p>
							<img src="img/<c:out value="${item.file}" />">
						</p>

						<p>
							価格<br>
							<c:out value="${item.price}" />
							円 <br>
						</p>

					</div>

					<div class="float-right">

						<h4>
							<c:out value="${item.name}" />
						</h4>
						<P>
							詳細 <br>
							<c:out value="${item.detail}" />
							<br> <br>
						</P>

					</div>

				</div>

			</c:when>

		</c:choose>

	</div>

</body>

</html>
