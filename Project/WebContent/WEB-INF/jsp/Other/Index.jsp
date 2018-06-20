<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<style>
body {
	width: 1000px;
	margin: 0 auto;
	text-align: center;
}

.wrapper {
	width: 500px;
	margin: 0 auto;
	border: 1px solid black;
	border-radius: 10px;
	text-align: center;
	padding: 20px;
	margin-top: 50px;
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>ホーム</h1>
		<br> <br>

		<div class="home">ストアもしくはフリーマケットで商品を購入する</div>

	</div>

</body>

</html>