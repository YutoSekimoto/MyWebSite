package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import dao.UserDao;

@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserDeleteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		try {

			//セッションスコープの有無を確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//管理者ログインか一般ログインか確認
			if(!(userSession.getLoginId().equals("admin"))) {

				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
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
				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
				return;
			}

			//データベース操作
			UserDao userDao = new UserDao();
			//ユーザーインスタンス作成
			UserBeans userDetail = userDao.UserSearchId(numberId);

			//ユーザーインスタンス取得成功
			if(userDetail != null) {

				//リクエストスコープにセット
				request.setAttribute("userDetail", userDetail);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/User/UserDelete.jsp");
				dispatcher.forward(request, response);
				return;

			}else {

				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
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

			//セッションスコープの有無を確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//管理者ログインか一般ログインか確認
			if(!(userSession.getLoginId().equals("admin"))) {

				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
				return;

			}

			String id = request.getParameter("id");

			//ゲットパラメータを数値に変換
			int numberId = 0;
			//数値への変換
			try {
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
				return;
			}

			//データベース操作
			UserDao userDao = new UserDao();
			String message = userDao.UserDelete(numberId);

			if(message != null) {//削除成功

				//エラーメッセージをセット
				request.setAttribute("eM", "削除しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/User/UserDelete.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//削除失敗

				//エラーメッセージをセット
				request.setAttribute("eM", "削除に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/User/UserDelete.jsp");
				dispatcher.forward(request, response);
				return;

			}

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

}
