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
 * Servlet implementation class ItemManagerUpdateServlet
 */
@WebServlet("/ItemManagerUpdateServlet")
public class ItemManagerUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemManagerUpdateServlet() {
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

		//IDを文字列から数値に変換
		int numberId = Integer.parseInt(id);

		//データベース操作
		ItemDao itemDao = new ItemDao();
		ItemBeans item = itemDao.ItemIdSearch(numberId);

		//リクエストスコープにセット
		request.setAttribute("item", item);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerUpdate.jsp");
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
		String name = request.getParameter("name");
		String detail = request.getParameter("detail");
		String price = request.getParameter("price");
		String imageFile = request.getParameter("imagefile");
		String id = request.getParameter("id");

		//未入力項目の確認
		if(name == "" || detail == "" || price == "" || imageFile == "" || id == "") {

			//ID文字列を数値に変換
			int numberId = Integer.parseInt(id);

			//データベース操作
			ItemDao itemDao = new ItemDao();
			ItemBeans item = itemDao.ItemIdSearch(numberId);

			//リクエストスコープにセット
			request.setAttribute("item", item);

			//エラーメッセーをセット
			request.setAttribute("eM", "未入力の項目があります");

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerUpdate.jsp");
			dispatcher.forward(request, response);
			return;

		}

		//価格とIDの文字列を数値に変換
		int numberPrice = Integer.parseInt(price);
		int numberId = Integer.parseInt(id);

		out.println(name);
		out.println(detail);
		out.println(price);
		out.println(imageFile);
		out.println(id);

		//データベース操作
		ItemDao itemDao = new ItemDao();
		String item = itemDao.ItemUpdate(numberId, name, detail, numberPrice, imageFile);

		//リクエストスコープにセット
		request.setAttribute("item", item);

		out.println(item);

	}

}
