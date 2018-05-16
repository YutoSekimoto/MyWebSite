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
 * Servlet implementation class UserTourokuServlet
 */
@WebServlet("/UserTourokuServlet")
public class UserTourokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserTourokuServlet() {
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

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserTouroku.jsp");
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
		String loginId = request.getParameter("loginid");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String birthDate = request.getParameter("birthdate");

		//日付設定
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String birthDateC = null;
		try {
			Date today = format.parse(birthDate);
			birthDateC = format.format(today);
		} catch (ParseException e) {
			e.printStackTrace();
		}


		//未入力項目の確認
		if(loginId == "" || password1 == "" || password2 == "" || name == "" || email == "" || birthDate == "") {

			//エラーメッセーをセット
			request.setAttribute("eM", "未入力の項目があります");

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserTouroku.jsp");
			dispatcher.forward(request, response);
			return;

		}

		//パスワードとパスワード確認が同等か確認
		if(!(password1.equals(password2))) {

			//エラーメッセーをセット
			request.setAttribute("eM", "パスワードが不一致です");

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserTouroku.jsp");
			dispatcher.forward(request, response);
			return;

		}

		//データベース処理
		UserDao userDao = new UserDao();
		String message = userDao.UserTouroku(loginId, password1, name, email, birthDateC);

		//リダイレクト(ユーザーリスト)
		response.sendRedirect("UserListServlet");

	}

}
