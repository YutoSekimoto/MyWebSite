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
 * Servlet implementation class UserDeleteServlet
 */
@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDeleteServlet() {
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

		//レスポンス
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		//リクエストパラメーターの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		//ゲットパラメータを取得し数値に変換
		String id = request.getParameter("id");
		int numberId = Integer.parseInt(id);

		//データベース操作
		UserDao userDao = new UserDao();
		UserBeans userDetail = userDao.UserSearchId(numberId);

		//リクエストスコープにセット
		request.setAttribute("userDetail", userDetail);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserDelete.jsp");
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

		//リクエストパラメータを取得し数値に変換
		String id = request.getParameter("id");
		int numberId = Integer.parseInt(id);

		//データベース操作
		UserDao userDao = new UserDao();
		String message = userDao.UserDelete(numberId);

		if(!(message == "")) {

			//リダイレクト(ユーザーリスト)
			response.sendRedirect("UserListServlet");

		}
		out.print(message);
	}

}
