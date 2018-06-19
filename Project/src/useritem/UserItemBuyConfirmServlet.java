package useritem;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import beans.UserItemBeans;
import dao.ChatDao;
import dao.UserBuyDao;
import dao.UserItemDao;

@WebServlet("/UserItemBuyConfirmServlet")
public class UserItemBuyConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserItemBuyConfirmServlet() {
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
			//IDから商品を取得
			UserItemBeans userItem = userItemDao.UserItemIdSearch(numberId);

			if(userItem != null) {//成功

				//リクエストスコープにセット
				request.setAttribute("userItem", userItem);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemBuyConfirm.jsp");
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

				UserBuyDao userBuyDao = new UserBuyDao();
				int userItemBuyId = userBuyDao.UserBuyTouroku(userItem.getName() , userSession.getId(), userItem.getFile(), userItem.getPrice());

				if(userItemBuyId != 0) {

					String fileName = userItemDao.UserItemDelete(numberId);

					//データベース操作
					ChatDao chatDao = new ChatDao();
					int record = chatDao.Delete(numberId);

					if(fileName != null && record != 0) {

						//商品購入履歴リストへリダイレクト
						response.sendRedirect("ItemBuyHistoryListServlet");
						return;

					}

				}

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
