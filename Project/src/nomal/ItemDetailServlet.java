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

import beans.ItemBeans;
import dao.ItemDao;

/**
 * Servlet implementation class ItemDetailServlet
 */
@WebServlet("/ItemDetailServlet")
public class ItemDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemDetailServlet() {
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
		ItemDao itemDao =  new ItemDao();
		ItemBeans item = itemDao.ItemIdSearch(numberId);

		//リクエストスコープにセット
		request.setAttribute("item", item);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemDetail.jsp");
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
		String id = request.getParameter("id");
		String number = request.getParameter("number");

		if(!(id.equals("")) && !(number.equals(""))) {

			//ID文字列を数値に変換
			int numberId = Integer.parseInt(id);
			int numberNumber = Integer.parseInt(number);

			//データベース操作
			ItemDao itemDao = new ItemDao();
			ItemBeans Item = itemDao.ItemIdSearch(numberId);

			//購入個数をセット
			Item.setNumber(numberNumber);

			//HttpSessionインスタンスの取得
			HttpSession session = request.getSession();

			ArrayList<ItemBeans> sItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");

			//商品カートセッションがnullの場合
			if(sItemList == null) {

				//商品カートセッションススコープに保存するコレクションインスタンス
				sItemList = new ArrayList<ItemBeans>();

			}

			//コレクションスコープにインスタンスを追加
			sItemList.add(Item);

			//セッションスコープにインスタンスを保存
			session.setAttribute("sItemList", sItemList);

			//商品カートリストへリダイレクト
			response.sendRedirect("ItemCartListServlet");

			out.println(sItemList);

		}

	}

}
