package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import dao.UserDao;

@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		try {

			//ユーザーセッションスコープの確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//データベース操作
			UserDao userDao = new UserDao();
			//ユーザーリストを取得
			ArrayList<UserBeans> userList = userDao.UserList();

			//リクエストスコープにセット
			request.setAttribute("userList", userList);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/User/UserList.jsp");
			dispatcher.forward(request, response);
			return;

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

			//ユーザーセッションスコープの確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//リクエストパラメータを取得
			String loginId = request.getParameter("loginId");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String birthDate = request.getParameter("birthDate");

			//入力項目の確認
			if(loginId.equals("") && name.equals("") && email.equals("") && birthDate.equals("")) {//未入力の場合ユーザーリスト全検索

				//データベース操作
				UserDao userDao = new UserDao();
				//インスタンス作成
				ArrayList<UserBeans> userList = userDao.UserList();

				//リクエストスコープにセット
				request.setAttribute("userList", userList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/User/UserList.jsp");
				dispatcher.forward(request, response);

			}else {//入力があった場合ユーザーリスト条件検索

				//データベース操作
				UserDao userDao = new UserDao();
				//インスタンス作成
				ArrayList<UserBeans> userList = userDao.UserSearch(loginId , name  , email , birthDate);

				//リクエストスコープにセット
				request.setAttribute("userList", userList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/User/UserList.jsp");
				dispatcher.forward(request, response);

			}

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

}
