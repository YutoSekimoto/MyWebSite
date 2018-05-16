<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>管理者/商品削除</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/Manager/ItemManagerDelete.css">
</head>

<body>

    <div class="wrapper">

        <div class="item-detail">

            <div class="float-left">
                <p><img src="img/<c:out value="${item.file}" />"></p>

                <p><c:out value="${item.price}" /><br></p>

                <form action="" method="post">
                    <input type="hidden" name="id" value="<c:out value="${item.id}" />">
                    <input type="submit" value="削除する">
                </form>

            </div>


            <div class="float-right">

                <h4><c:out value="${item.name}" /></h4>
                <P>
                    <br>詳細
                    <br><c:out value="${item.detail}" />
                    <br><br>
                </P>

            </div>

        </div>

    </div>

</body>

</html>
