<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="beans.UserItemBeans"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>フリーマーケット商品詳細</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/UserItem/UserItemBuyDetail.css">
</head>

<body>

	<%
	    //セッション・リクエストスコープを取得
		UserBeans jspUser = (UserBeans) session.getAttribute("usersession");
		UserItemBeans jspUserItem = (UserItemBeans) request.getAttribute("userItem");

		//セッション・リクエストスコープからユーザーの名前と商品IDを取得
		String name = jspUser.getName();
		int itemId = jspUserItem.getId();
	%>

	<jsp:include page="/WEB-INF/jsp/Other/Header.jsp" />

	<div class="wrapper">

		<h1>フリーマーケット商品詳細</h1>
		<br> <br>

		<div class="item-detail">
			<div class="float-left">
				<p>
					<img src="img/<c:out value="${userItem.file}" />">
				</p>
				<p>
					価格<br>
					<c:out value="${userItem.price}" />
					円<br>
				</p>
				<br> <a href="UserItemBuySearchListServlet" class="btn btn-primary">フリーマーケット商品検索へ</a> <a href="UserItemBuyConfirmServlet?id=<c:out value="${userItem.id}" />" class="btn btn-primary">購入確認画面へ</a>
			</div>
			<br> <br>

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

			<div class="comment-form">
				<div id="update"></div>

				<form action="" method="post">
					<textarea name="comment" id="comment" required></textarea>
					<br> <input type="submit" id="toukou" class="btn btn-dark" value="投稿"> <input type="reset" class="btn btn-dark" value="削除">
				</form>
				<input type="button" id="kousin" class="btn btn-dark" value="更新">
			</div>

			<div id="comment-list">
				<c:forEach var="chat" items="${chatList}">
					<div class="name-date">
						<c:out value="${chat.name}" />
						<c:out value="${chat.formatDate}" />
					</div>

					<div class="chat-comment">
						<c:out value="${chat.comment}" />
					</div>
				</c:forEach>
				<br>
			</div>
		</div>

	</div>

	<script>
        document.addEventListener("DOMContentLoaded", function() {

            var ws = new WebSocket('ws://localhost:8080/EcSite/broadcast');

            //オープン
            ws.onopen = function() {

            //window.alert("オープン");

            }

            //メッセージ
            ws.onmessage = function(message) {

                //window.alert("メッセージ");
                var chats = JSON.parse(message.data);

                //既存のコメントを削除
                var commentListDelete = document.getElementById("comment-list");
                while (commentListDelete.firstChild) {

                    commentListDelete.removeChild(commentListDelete.firstChild);

                }

                //コメント一覧をを取得して表示
                for(var i = 0; i < Object.keys(chats).length; i++){


                	   //オブジェクトのキーに変数の値を使用
                    var chat = "chat" + i;

                	   //コメントリストを取得
                    var commentList = document.getElementById("comment-list");

                	   //タグを生成しクラスプロパティの設定
                    var nameDateDiv = document.createElement("div");
                    nameDateDiv.className = "name-date";
                    var chatCommentDiv = document.createElement("div");
                    chatCommentDiv.className = "chat-comment";
                    var br = document.createElement("br");

                    //ウェブソケットで送られてきた値を取得しテキストノードを設定
                    var ndText = document.createTextNode("名前：" + chats[chat].name + "日付：" + chats[chat].date);
                    var ccText = document.createTextNode(chats[chat].comment);

                    //divタグにテキストノードを追加
                    nameDateDiv.appendChild(ndText);
                    chatCommentDiv.appendChild(ccText);

                    //commentListに全てのタグを追加
                    commentList.appendChild(nameDateDiv);
                    commentList.appendChild(chatCommentDiv);
                    commentList.appendChild(br);

                    //投稿したコメントを削除
                	    document.getElementById("comment").value = "";

                }

            }

            //クローズ
            ws.onclose = function() {

            //window.alert("クローズ");

            }

            //投稿フォーム
            document.getElementById("toukou").addEventListener("click", function(e) {

            	//イベントをキャンセル
            	e.preventDefault();

            	//投稿されたコメントを取得
            	var commentConfirm =  document.getElementById("comment").value;

            	//コメントが空でなければ実行
            	if(commentConfirm !== ""){

            		//ユーザー名と商品IDを設定
            	 	var name = '<%=name%>';
                	var itemId = '<%=itemId%>';

                	//コメントを設定
    				var comment = document.getElementById("comment").value;

    				//window.alert("投稿," + itemId + "," + name + "," + comment);
    				//ウェブソケットに送信
    				ws.send("投稿," + itemId + "," + name + "," + comment);

            	}

			}, false)

			//更新フォーム
			document.getElementById("kousin").addEventListener("click", function(e) {

				//イベントをキャンセル
                e.preventDefault();

                //商品IDを設定
                var itemId = '<%=itemId%>';

				//window.alert("更新," + itemId);
				//ウェブソケットに送信
				ws.send("更新," + itemId);

			}, false)

		}, false)
	</script>
</body>

</html>
