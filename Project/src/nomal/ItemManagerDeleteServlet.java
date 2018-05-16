package nomal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ItemBeans;
import dao.ItemDao;

/**
 * Servlet implementation class ItemManagerDeleteServlet
 */
@WebServlet("/ItemManagerDeleteServlet")
public class ItemManagerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemManagerDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//リクエストパラーメータを取得
		String id = request.getParameter("id");

		//ID文字列を数値に変換
		int numberId = Integer.parseInt(id);

		//データベース操作
		ItemDao itemDao = new ItemDao();
		ItemBeans item = itemDao.ItemIdSearch(numberId);

		//リクエストスコープにセット
		request.setAttribute("item", item);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerDelete.jsp");
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

		//リクエストパラーメータを取得
		String id = request.getParameter("id");

		//ID文字列を数値に変換
		int numberId = Integer.parseInt(id);

		//データベース操作
		ItemDao itemDao = new ItemDao();
		String message = itemDao.ItemDelete(numberId);

		//商品リストへリダイレクト
		response.sendRedirect("ItemManagerSearchList");

	}

}
