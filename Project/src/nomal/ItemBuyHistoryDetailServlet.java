package nomal;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyBeans;
import beans.DeliveryBeans;
import beans.ItemBeans;
import beans.UserBeans;
import dao.BuyDao;
import dao.BuyDetailDao;
import dao.DeliveryDao;
import dao.ItemDao;

@WebServlet("/ItemBuyHistoryDetailServlet")
public class ItemBuyHistoryDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemBuyHistoryDetailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		try {

			//ユーザーセッションスコープの有無を確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//ゲットパラメータを取得
			String id = request.getParameter("id");

			//ゲットパラメータを数値に変換
			int numberId = 0;
			//数値への変換
			try {
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//商品購入履歴リストへリダイレクト
				response.sendRedirect("ItemBuyHistoryListServlet");
				return;
			}

			//データベース操作
			BuyDao buyDao = new BuyDao();
			//IDから購入履歴取得
			BuyBeans buy = buyDao.BuyId(numberId);

			//データベース操作
			DeliveryDao deliveryDao = new DeliveryDao();
			//配送方法IDから配送の名前、値段を取得
			DeliveryBeans delivery = deliveryDao.DeliveryIdMethod2(buy.getDeliveryId());

			//配送方法、価格をセット
			buy.setDeliveryMethod(delivery.getName());
			buy.setDeliveryPrice(delivery.getPrice());

			//データベース操作
			BuyDetailDao buyDetailDao = new BuyDetailDao();
			//IDから購入商品IDと個数リストを取得しインスタンスに保存
			ArrayList<ItemBeans> forItemList = buyDetailDao.BuyItemIdList(numberId);

			//データベース操作/購入商品IDリストから商品情報を取得しコレクションインスタンスに追加
			ItemDao itemDao = new ItemDao();
			ArrayList<ItemBeans> itemList = new ArrayList<ItemBeans>();
			for(ItemBeans item : forItemList) {

				ItemBeans returnItem = itemDao.ItemIdSearch(item.getId());

				returnItem.setNumber(item.getNumber());

				itemList.add(returnItem);

			}

			if(buy != null && itemList != null) {

				//リクエストスコープにインスタンスをセット
				request.setAttribute("buy", buy);
				request.setAttribute("itemList", itemList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemBuyHistoryDetail.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//商品購入履歴リストへリダイレクト
			response.sendRedirect("ItemBuyHistoryListServlet");
			return;

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
