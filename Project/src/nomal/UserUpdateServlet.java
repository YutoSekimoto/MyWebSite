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

@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserUpdateServlet() {
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
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
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

		try {


			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//リクエストパラメータ取得
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String birthDate = request.getParameter("birthDate");
			String id = request.getParameter("id");

			//IDを数値に変換
			int numberId = 0;
			//数値への変換
			try {
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
				return;
			}

			//誕生日入力の確認
			if(birthDate == "") {

				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
				return;

			}

			//誕生日設定
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String birthDateFormat = null;
			try {
				Date birthDateDate = format.parse(birthDate);
				birthDateFormat = format.format(birthDateDate);
			} catch (ParseException e) {
				//ユーザーリストへリダイレクト
				response.sendRedirect("UserListServlet");
				return;
			}

			//入力の確認
			if(password1.equals("") || password2.equals("") || name.equals("") || address.equals("")|| birthDate.equals("")) {

				//データベース操作
				UserDao userDao = new UserDao();
				UserBeans userDetail = userDao.UserSearchId(numberId);

				//リクエストスコープにセット
				request.setAttribute("userDetail", userDetail);

				//エラーメッセージ
				request.setAttribute("eM", "未入力の項目があります");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//パスワード比較
			if(!(password1.equals(password2))){

				//データベース操作
				UserDao userDao = new UserDao();
				UserBeans userDetail = userDao.UserSearchId(numberId);

				//リクエストスコープにセット
				request.setAttribute("userDetail", userDetail);

				//エラーメッセージ
				request.setAttribute("eM", "パスワードが一致しません");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//データベース操作
			UserDao userDao = new UserDao();
			//ユーザーアップデート
			String message = userDao.UserUpdate(numberId , password1 , name , address , birthDateFormat) ;

			if(message != "") {//アップデート成功

				//エラーメッセージ
				request.setAttribute("rM", "更新しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//アップデート失敗

				//エラーメッセージ
				request.setAttribute("rM", "更新に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
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
