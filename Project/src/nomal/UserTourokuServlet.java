package nomal;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import dao.UserDao;

@WebServlet("/UserTourokuServlet")
public class UserTourokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserTourokuServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserTouroku.jsp");
			dispatcher.forward(request, response);
			return;

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//リクエストパラメータ取得
			String loginId = request.getParameter("loginid");
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String birthDate = request.getParameter("birthdate");

			//日付設定
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String birthDateFormat = null;
			try {
				Date today = format.parse(birthDate);
				birthDateFormat = format.format(today);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			//未入力項目の確認
			if(loginId.equals("") || password1.equals("") || password2.equals("") || name.equals("") || email.equals("") || address.equals("")|| birthDate.equals("")) {

				//エラーメッセージをセット
				request.setAttribute("eM", "未入力の項目があります");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserTouroku.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//パスワード一致の確認
			if(!(password1.equals(password2))) {

				//エラーメッセージをセット
				request.setAttribute("eM", "パスワードが不一致です");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserTouroku.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//データベース処理
			UserDao userDao = new UserDao();
			//ユーザー登録
			int idKey = userDao.UserTouroku(loginId, password1, name, email, address, birthDateFormat);

			if(idKey != 0) {//成功

				//ユーザーインスタンス生成
				UserBeans user = userDao.UserLoginId(idKey);

				//HttpSessionインスタンスの取得
				HttpSession session = request.getSession();
				//ユーザーセッションスコープにインスタンスを保存
				session.setAttribute("usersession", user);

				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");

			}else {//失敗

				//エラーメッセージをセット
				request.setAttribute("eM", "ユーザー登録に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserTouroku.jsp");
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
