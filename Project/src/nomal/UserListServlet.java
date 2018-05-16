
package nomal;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class UserListServlet
 */
@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListServlet() {
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

		//データベース操作
		UserDao userDao = new UserDao();
		ArrayList<UserBeans> userList = userDao.UserList();

		//リクエストスコープにセット
		request.setAttribute("userList", userList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserList.jsp");
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

		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String birthDate = request.getParameter("birthDate");

		//未入力検索は全て検索
		if(loginId == "" && name == "" && email == "" && birthDate == "") {

			UserDao userDao = new UserDao();
			ArrayList<UserBeans> userList = userDao.UserList();
			out.println(userList);

			//リクエストスコープにセット
			request.setAttribute("userList", userList);

			//フォワード
	      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserList.jsp");
	      	dispatcher.forward(request, response);

		}else {

			UserDao userDao = new UserDao();
			ArrayList<UserBeans> userList = userDao.UserSearch(loginId , name  , email , birthDate);

			//リクエストスコープにセット
			request.setAttribute("userList", userList);

			//フォワード
	      	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserList.jsp");
	      	dispatcher.forward(request, response);

		}

	}

}
