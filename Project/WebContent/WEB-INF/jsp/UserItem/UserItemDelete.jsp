<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>ユーザー/商品削除</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/UserItem/UserItemDelete.css">
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>ユーザー商品削除</h1>
		<br> <br>

		<c:choose>

			<c:when test="${eM != null}">
				<div class="item-result">
					<c:out value="${eM}" />
					<br> <a href="UserItemSearchListServlet">ユーザー商品検索リストへ</a>
				</div>
			</c:when>

			<c:otherwise>

				<div class="item-detail">

					<div class="float-left">
						<p>
							<img src="img/<c:out value="${userItem.file}" />">
						</p>

						<p>
							価格<br>
							<c:out value="${userItem.price}" />
							円
						</p>
						<br>

						<p>
							<strong>この商品を削除してもよろしいですか</strong>
						</p>

						<form action="UserItemDeleteServlet" method="post">
							<input type="hidden" name="id"
								value="<c:out value="${userItem.id}" />"> <a
								href="UserItemSearchListServlet" class="btn btn-primary">キャンセル</a>
							<input type="submit" class="btn btn-primary" value="削除する">
						</form>
					</div>

					<div class="float-right">
						<h4>
							<c:out value="${userItem.name}" />
						</h4>
						<P>
							<br> <strong>＜詳細＞</strong><br>
							<c:out value="${userItem.detail}" />
							<br> <br>
						</P>
					</div>

				</div>

			</c:otherwise>

		</c:choose>

	</div>

</body>

</html>
