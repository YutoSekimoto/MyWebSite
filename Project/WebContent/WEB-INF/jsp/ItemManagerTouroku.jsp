<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>管理者/商品登録</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/Manager/ItemManagerTouroku.css">
</head>

<body>

    <div class="wrapper">

    <c:if test="${eM != null}">

    <div class="alert alert-danger" role="alert">
    <c:out value="${eM}" />
    </div>

    </c:if>

        <h1>商品登録</h1>

        <div class="form-search">

            <form action="ItemManagerTourokuServlet" method="post">
                <div class="form-group">
                    <label for="exampleInputName">商品名</label>
                    <input type="text" class="form-control" id="exampleInputName" placeholder="Name" name="name">
                </div>
                <div class="form-group">
                    <label for="exampleInputDetail">詳細</label>
                    <textarea name="detail" class="form-control" rows="10" id="exampleInputDetail" placeholder="Detail" name="detail"></textarea>
                </div>
                <div class="form-group">
                    <label for="exampleInputPrice">値段</label>
                    <input type="number" class="form-control" id="exampleInputPrice" placeholder="Price" name="price">
                </div>
                <div class="form-group">
                    <label for="exampleInputImage">画像ファイル</label>
                    <input type="text" class="form-control" id="exampleInputImage" placeholder="Image" name="imageFile">
                </div>
                <button type="submit" class="btn btn-secondary">登録</button>
            </form>

        </div>
        <br><br><br><br>

        <div class="item-detail">

            <h1>商品登録結果</h1>

            <div class="float-left">
                <p><img src="img/<c:out value="${item.file}" />"></p>

                <p>価格<br><c:out value="${item.price}" /><br></p>

            </div>


            <div class="float-right">

                <h4><c:out value="${item.name}" /></h4>
                <P>
                    詳細
                    <br>
                    <c:out value="${item.detail}" />
                    <br><br>
                </P>

            </div>

        </div>
    </div>

</body>

</html>
