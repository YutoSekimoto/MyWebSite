package nomal;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		if(userSession != null) {

			//リダイレクト(ユーザーリスト)
			response.sendRedirect("UserListServlet");
			return;

		}

		//フォワード
      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
      	dispatcher.forward(request, response);

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

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();
		//セッションスコープの有無を確認
		UserBeans userSession = (UserBeans)session.getAttribute("usersession");
		if(userSession != null) {

			//リダイレクト(ユーザーリスト)
			response.sendRedirect("UserListServlet");
			return;

		}


		String loginId  = request.getParameter("loginid");
		String password = request.getParameter("password");

		//未入力項目の確認
		if(loginId == "" || password == "") {

			//エラーメッセーをセット
			request.setAttribute("eM", "未入力の項目があります");

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;

		}

		//データベース操作
		UserDao userDao = new UserDao();
		//インスタンスの生成
		UserBeans user = userDao.UserLogin(loginId , password);

		//ログイン失敗
		if(user == null) {

			//エラーメッセーをセット
			request.setAttribute("eM", "入力した値が一致しません");

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;

		}

		//セッションスコープにインスタンスを保存
		session.setAttribute("usersession", user);

		//セッションスコープ確認用
		UserBeans user1 = (UserBeans)session.getAttribute("usersession");
		out.println(user1.getId());
		out.println(user1.getLoginId());
		out.println(user1.getPassword());
		out.println(user1.getName());
		out.println(user1.getEmail());
		out.println(user1.getCreateDate());
		out.println(user1.getBirthDate());
		out.println(user1.getUpdateDate());

		//リダイレクト(ユーザーリスト)
		response.sendRedirect("UserListServlet");
		return;

	}

}
