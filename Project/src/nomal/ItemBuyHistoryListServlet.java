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

import beans.BuyBeans;
import dao.BuyDao;
import dao.DeliveryDao;

/**
 * Servlet implementation class ItemBuyHistoryServlet
 */
@WebServlet("/ItemBuyHistoryListServlet")
public class ItemBuyHistoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemBuyHistoryListServlet() {
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

		//データベース操作/ユーザーIDで商品購入履歴リストを取得
		BuyDao buyDao = new BuyDao();
		ArrayList<BuyBeans> buyList = buyDao.BuyList(1);

		//データベース操作/配送IDから配送方法をセット
		DeliveryDao deliveryDao =  new DeliveryDao();
		for(BuyBeans buy : buyList) {

			buy.setDeliveryMethod(deliveryDao.DeliveryIdMethod(buy.getDeliveryId()));

		}

		//リクエストスコープをセット
		request.setAttribute("buyList", buyList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemBuyHistoryList.jsp");
		dispatcher.forward(request, response);
		return;


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
