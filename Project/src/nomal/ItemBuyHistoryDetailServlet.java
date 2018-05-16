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
import beans.DeliveryBeans;
import beans.ItemBeans;
import dao.BuyDao;
import dao.BuyDetailDao;
import dao.DeliveryDao;
import dao.ItemDao;

/**
 * Servlet implementation class ItemBuyHistoryDetailServlet
 */
@WebServlet("/ItemBuyHistoryDetailServlet")
public class ItemBuyHistoryDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemBuyHistoryDetailServlet() {
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

		//リクエストパラメータを取得
		String id = request.getParameter("id");

		//IDを文字列から数値へ変換
		int numberId = Integer.parseInt(id);

		//データベース操作/IDkから購入履歴取得
		BuyDao buyDao = new BuyDao();
		BuyBeans buy = buyDao.BuyId(numberId);

		//データベース操作/配送方法IDから配送の名前、値段を取得
		DeliveryDao deliveryDao = new DeliveryDao();
		DeliveryBeans delivery = deliveryDao.DeliveryIdMethod2(buy.getDeliveryId());

		//配送方法、価格をセット
		buy.setDeliveryMethod(delivery.getName());
		buy.setDeliveryPrice(delivery.getPrice());

		//データベース操作/IDから購入商品IDと個数リストを取得しインスタンスに保存
		BuyDetailDao buyDetailDao = new BuyDetailDao();
		ArrayList<ItemBeans> forItemList = buyDetailDao.BuyItemIdList(numberId);

		//データベース操作/購入商品IDリストから商品情報を取得しコレクションインスタンスに追加
		ItemDao itemDao = new ItemDao();
		ArrayList<ItemBeans> itemList = new ArrayList<ItemBeans>();
		for(ItemBeans item : forItemList) {

			ItemBeans returnItem = itemDao.ItemIdSearch(item.getId());

			returnItem.setNumber(item.getNumber());

			itemList.add(returnItem);

		}

		//リクエストスコープにインスタンスをセット
		request.setAttribute("buy", buy);
		request.setAttribute("itemList", itemList);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemBuyHistoryDetail.jsp");
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
