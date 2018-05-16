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
 * Servlet implementation class ItemManagerTourokuServlet
 */
@WebServlet("/ItemManagerTourokuServlet")
public class ItemManagerTourokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemManagerTourokuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerTouroku.jsp");
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


		//リクエストパラメータを取得
		String name = request.getParameter("name");
		String detail = request.getParameter("detail");
		String imageFile = request.getParameter("imageFile");
		String price = request.getParameter("price");

		//未入力を確認
		if(name == "" || detail == "" || imageFile == "" || price == "") {

			//エラーメッセーをセット
			request.setAttribute("eM", "未入力の項目があります");

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerTouroku.jsp");
			dispatcher.forward(request, response);
			return;

		}

		//値段を文字列から数値に変換
		int numberPrice = Integer.parseInt(price);

		out.println(name);
		out.println(detail);
		out.println(imageFile);
		out.println(numberPrice);

		//データベース操作
		ItemDao itemDao = new ItemDao();
		ItemBeans item = itemDao.ItemTouroku(name , detail , numberPrice , imageFile);

		if(item != null) {

			request.setAttribute("item", item);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerTouroku.jsp");
			dispatcher.forward(request, response);
			return;

		}

	}

}
