package useritem;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ChatBeans;
import beans.UserBeans;
import beans.UserItemBeans;
import dao.ChatDao;
import dao.UserItemDao;

@WebServlet("/UserItemBuyDetailServlet")
public class UserItemBuyDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserItemBuyDetailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		try {

			//ユーザーセッションスコープの有無を確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//ゲットパラメータを取得
			String id = request.getParameter("id");

			//ゲットパラメータを数値に変換
			int numberId = 0;
			//数値への変換
			try {
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//ユーザー商品購入検索リストへリダイレクト
				response.sendRedirect("UserItemBuySearchListServlet");
				return;
			}

			//データベース操作
			UserItemDao userItemDao = new UserItemDao();
			ChatDao chatDao = new ChatDao();

			//IDから商品を取得
			UserItemBeans userItem = userItemDao.UserItemIdSearch(numberId);
			//IDからコメントを取得
			ArrayList<ChatBeans> chatList = chatDao.CommentList(numberId);

			//日付をフォーマット
			for(ChatBeans chat : chatList) {
				chat.setFormatDate(chat.getCreateDate());
			}

			if(userItem != null && chatList != null) {//成功

				//リクエストスコープにセット
				request.setAttribute("userItem", userItem);
				request.setAttribute("chatList", chatList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemBuyDetail.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//ユーザー商品購入検索リストへリダイレクト
				response.sendRedirect("UserItemBuySearchListServlet");
				return;

			}

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		try {

			//ユーザーセッションスコープの有無を確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//ゲットパラメーターを取得
			String updata = request.getParameter("update");
			String itemId = request.getParameter("itemId");

			//アップデートフォーム
			if(updata != null && itemId != null && !itemId.equals("")) {

				//ゲットパラメータを数値に変換
				int numberItemId = 0;
				//数値への変換
				try {
					numberItemId = Integer.parseInt(itemId);
				} catch (NumberFormatException nfex) {
					//ユーザー商品購入検索リストへリダイレクト
					response.sendRedirect("UserItemBuySearchListServlet");
					return;
				}

				if(numberItemId != 0) {//成功

					//ユーザー商品購入詳細へリダイレクト
					response.sendRedirect("UserItemBuyDetailServlet?id=" + numberItemId + "#update");
					return;

				}else {//失敗

					//ユーザー商品購入検索リストへリダイレクト
					response.sendRedirect("UserItemBuySearchListServlet");
					return;

				}

			}

			/*
			//コメントフォーム
			//コメントとアイテムIDのゲットパラメータを取得
			String comment = request.getParameter("comment");

			//ゲットパラメータを数値に変換
			int numberItemId = 0;
			//数値への変換
			try {
				numberItemId = Integer.parseInt(itemId);
			} catch (NumberFormatException nfex) {
				//ユーザー商品購入検索リストへリダイレクト
				response.sendRedirect("UserItemBuySearchListServlet");
				return;
			}

			//ゲットパラメータの有無確認
			if(!(comment.equals("")) && !(itemId.equals("")) && !(userSession.getName().equals("")) && userSession.getName() != null){

				ChatDao chatDao = new ChatDao();
				int chatId = chatDao.CommentTouroku(numberItemId, userSession.getName(), comment);

				if(chatId != 0) {

					//ユーザー商品購入詳細へリダイレクト
					response.sendRedirect("UserItemBuyDetailServlet?id=" + numberItemId + "#update");
					return;
				}

			}else {

				//ユーザー商品購入検索リストへリダイレクト
				response.sendRedirect("UserItemBuySearchListServlet");
				return;

			}
			 */

			//購入フォーム
			//ゲットパラメータを取得
			String id = request.getParameter("id");

			//ゲットパラメータを数値に変換
			int numberId = 0;
			//数値への変換
			try {
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//ユーザー商品購入検索リストへリダイレクト
				response.sendRedirect("UserItemBuySearchListServlet");
				return;
			}

			//データベース操作
			UserItemDao userItemDao = new UserItemDao();
			//IDから商品を取得
			UserItemBeans userItem = userItemDao.UserItemIdSearch(numberId);

			if(userItem != null) {//成功

				//リクエストスコープにセット
				request.setAttribute("userItemConfirm", userItem);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemBuyDetail.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//ユーザー商品購入検索リストへリダイレクト
				response.sendRedirect("UserItemBuySearchListServlet");
				return;

			}

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

}
