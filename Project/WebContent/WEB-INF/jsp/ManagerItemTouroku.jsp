<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>管理者/商品登録</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Manager/ManagerItemTouroku.css">
</head>

<body>

	<jsp:include page="/baselayout/header.jsp" />

	<div class="wrapper">

		<c:if test="${eM != null}">

			<div class="eM">
				☆<c:out value="${eM}" />☆
				<br> <a href="ManagerItemSearchListServlet">管理者商品リストへ</a>
			</div>

		</c:if>

		<h1>商品登録</h1>

		<div class="form-search">

			<form action="ManagerItemTourokuServlet" method="post">
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
					<label for="exampleInputImage">画像ファイル</label> <input type="text"
						class="form-control" id="exampleInputImage" placeholder="Image"
						name="imageFile" required>
				</div>
				<a href="ManagerItemSearchListServlet" class="btn btn-primary">キャンセル</a>
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
