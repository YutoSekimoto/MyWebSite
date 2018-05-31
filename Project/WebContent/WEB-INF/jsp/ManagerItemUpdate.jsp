<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>管理者/商品更新</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/Manager/ManagerItemUpdate.css">
</head>

<body>

	<jsp:include page="/baselayout/header.jsp" />

	<div class="wrapper">

		<c:if test="${eM != null}">

			<div class="alert alert-danger" role="alert">
				<c:out value="${eM}" />
			</div>

		</c:if>

		<h1>商品更新</h1>
		<c:choose>

			<c:when test="${rM != null}">
				<div class="item-result">
					<c:out value="${rM}" />
					<br> <a href="ManagerItemSearchListServlet">商品管理者リストへ</a>
				</div>
			</c:when>

			<c:otherwise>
				<div class="form-search">

					<form action="ManagerItemUpdateServlet" method="post">
						<div class="form-group">
							<label for="exampleInputName">商品名</label> <input type="text"
								class="form-control" id="exampleInputName" placeholder="Name"
								value="<c:out value="${item.name}" />" name="name" required>
						</div>
						<div class="form-group">
							<label for="exampleInputDetail">詳細</label>
							<textarea name="detail" class="form-control" rows="10"
								id="exampleInputDetail" placeholder="Detail" name="detail"
								required><c:out value="${item.detail}" /></textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputPrice">値段</label> <input type="number"
								class="form-control" id="exampleInputPrice" placeholder="Price"
								value="<c:out value="${item.price}" />" name="price" required>
						</div>
						<div class="form-group">
							<label for="exampleInputImage">画像ファイル</label> <input type="text"
								class="form-control" id="exampleInputImage" placeholder="Image"
								value="<c:out value="${item.file}" />" name="imagefile" required>
						</div>
						<input type="hidden" name="id"
							value="<c:out value="${item.id}" />" name="id"> <a
							href="ManagerItemSearchListServlet" class="btn btn-primary">キャンセル</a>
						<input type="submit" class="btn btn-primary" value="更新">
					</form>
				</div>
			</c:otherwise>

		</c:choose>

	</div>

</body>

</html>
