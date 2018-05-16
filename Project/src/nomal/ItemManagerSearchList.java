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

import beans.ItemBeans;
import dao.ItemDao;

/**
 * Servlet implementation class ItemManagerSearchList
 */
@WebServlet("/ItemManagerSearchList")
public class ItemManagerSearchList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemManagerSearchList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//レスポンス
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		//リクエストパラメーターの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		//データベース操作
		ItemDao itemDao = new ItemDao();
		ArrayList<ItemBeans> itemList = itemDao.ItemList();
		//String itemList = itemDao.ItemList();

		//リクエストスコープにセット
		request.setAttribute("itemList", itemList);

		out.println(itemList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerSearchList.jsp");
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

		//価格が未入力の場合
		int numberPrice = 0;
		if(!(price.equals(""))) {
			//文字列を数値に変化
			numberPrice = Integer.parseInt(price);
		}

		out.println(name);
		out.println(detail);
		out.println(price);
		out.println(imageFile);

		//データベース操作
		ItemDao itemDao = new ItemDao();
		ArrayList<ItemBeans> itemList = itemDao.ItemSearchList(name , detail , numberPrice , imageFile);

		//リクエストスコープにセット
		request.setAttribute("itemList", itemList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemManagerSearchList.jsp");
		dispatcher.forward(request, response);
		return;

	}

}
