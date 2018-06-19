<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="beans.ItemBeans"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<!DOCTYPE html>
<head>
<style>
.header {
	margin-top: 20px;
	border-bottom: 1px solid black;
	padding: 30px;
	overflow: hidden;
	font-family: 游ゴシック体, 'Yu Gothic', YuGothic, 'ヒラギノ角ゴシック Pro',
		'Hiragino Kaku Gothic Pro', メイリオ, Meiryo, Osaka, 'ＭＳ Ｐゴシック',
		'MS PGothic', sans-serif;
}

.header h1 {
	float: left;
}

.header h1 a {
	color: black;
	text-decoration: none;
}

.header .user-detail {
	float: right;
	font-weight: bold;
	padding: 10px;
	text-align: center;
}

.header .log {
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid black;
}

.header .a {
	margin-bottom: 20px;
}

.header .header-navi {
	margin: 0 auto;
	width: 600px;
}

.header .header-navi ul {
	list-style: none;
	margin: 0;
	padding: 0;
	margin-left: 10px;
}

.header .header-navi li {
	float: left;
	text-align: center;
	padding: 20px;
	margin-top: 80px;
	width: 170px;
	border: 1px solid black;
}

.header .header-navi a:hover {
	color: blue;
	font-weight: bold;
}
</style>
</head>

<div class="header">

	<h1>
		<a href="IndexServlet">ECタイトル</a>
	</h1>

	<div class="user">

		<div class="user-detail">

			<div class="log">
				<c:choose>

					<c:when test="${usersession != null}">
						<c:out value="${usersession.name}" />/
                        <a href="LogoutServlet">ログアウト</a>
					</c:when>

					<c:otherwise>
						<a href="LoginServlet">ログイン</a>/
                            <a href="UserTourokuServlet">新規登録</a>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="a">
				<a href="UserListServlet">ユーザー管理画面</a><br> <a
					href="ItemBuyHistoryListServlet">購入履歴</a>
			</div>


			<c:if test="${usersession.loginId == 'admin'}">
				<div class="a">
					<a href="ManagerItemSearchListServlet">★管理者商品管理画面★</a>
				</div>
			</c:if>

			<c:if test="${usersession.loginId != null}">
				<div class="a">
					<a href="UserItemSearchListServlet">★ユーザー商品管理画面★</a>
				</div>
			</c:if>

			<div class="cart">

				<%
				//カートの個数変数を設定
					int totalNumber = 0;
				%>

				<a href="ItemCartListServlet"> カート <c:if
						test="${sItemList != null && !sItemList.isEmpty() && usersession != null}">
						<%
						//セッションスコープを取得
							ArrayList<ItemBeans> jspItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");
								UserBeans jspUser = (UserBeans) session.getAttribute("usersession");

								//カートの個数を設定
								for (ItemBeans jspItem : jspItemList) {

									//ユーザーによって振り分け
									if (jspItem.getUserId() == jspUser.getId()) {
										totalNumber += jspItem.getNumber();
									}

								}
						%>

					</c:if> <%=totalNumber%>個<img src="img/cart.png"></a>

			</div>

		</div>

		<nav class="header-navi">

			<ul>
				<li><a href="IndexServlet">ホーム</a></li>
				<li><a href="ItemListServlet">ストア</a></li>
				<li><a href="UserItemBuySearchListServlet">フリーマケット</a></li>
			</ul>

		</nav>

	</div>

</div>