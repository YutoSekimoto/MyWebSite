<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>エラー</title>
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

		<div class="error">エラーが発生しました</div>

	</div>

</body>

</html>