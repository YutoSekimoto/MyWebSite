package nomal;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();
		//セッションスコープの有無を確認
		UserBeans userSession = (UserBeans)session.getAttribute("usersession");
		if(userSession == null) {

			//リダイレクト(ユーザーリスト)
			response.sendRedirect("LoginServlet");
			return;

		}

		//ゲットパラメータを取得し数値に変換
		String id = request.getParameter("id");
		int numberId = Integer.parseInt(id);

		//データベース操作
		UserDao userDao = new UserDao();
		UserBeans userDetail = userDao.UserSearchId(numberId);

		//リクエストスコープにセット
		request.setAttribute("userDetail", userDetail);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
		dispatcher.forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		//レスポンス
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		//リクエストパラメーターの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		//リクエストパラメータ取得
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String birthDate = request.getParameter("birthDate");
		//ゲットパラメータを取得し数値に変換
		String id = request.getParameter("id");
		int numberId = Integer.parseInt(id);

		//日付設定
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String birthDateC = null;
		try {
			Date today = format.parse(birthDate);
			birthDateC = format.format(today);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//未入力チェック
		if(password1 == "" || password2 == "" || name == "" || birthDate == "") {

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
		String message = userDao.UserUpdate(numberId, password1, name, birthDateC);

		if(!(message == "")) {

			//ユーザーリストへリダイレクト
			response.sendRedirect("UserListServlet");

		}

		out.println(password1);
		out.println(password2);
		out.println(name);
		out.println(birthDateC);

	}

}
