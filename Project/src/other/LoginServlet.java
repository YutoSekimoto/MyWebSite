
package other;

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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		try {

			//ユーザーセッションスコープ有無確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession != null) {

				//商品リストへリダイレクト
				response.sendRedirect("ItemListServlet");
				return;

			}

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Other/login.jsp");
			dispatcher.forward(request, response);

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

			//ユーザーセッションセッションスコープの確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession != null) {

				//商品リストへリダイレクト
				response.sendRedirect("ItemListServlet");
				return;

			}

			//リクエストパラーメータを取得
			String loginId  = request.getParameter("loginid");
			String password = request.getParameter("password");

			//未入力項目確認
			if(loginId.equals("") || password.equals("")) {

				//エラーメッセージをセット
				request.setAttribute("eM", "未入力の項目があります");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Other/login.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//データベース操作
			UserDao userDao = new UserDao();
			//ユーザーインスタンス生成
			UserBeans user = userDao.UserLogin(loginId , password);

			//ログイン失敗
			if(user == null) {

				//エラーメッセージをセット
				request.setAttribute("eM", "入力した値が一致しません");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Other/login.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//ユーザーセッションスコープにインスタンスを保存
			session.setAttribute("usersession", user);

			//商品リストへリダイレクト
			response.sendRedirect("ItemListServlet");
			return;

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}



	}

}
