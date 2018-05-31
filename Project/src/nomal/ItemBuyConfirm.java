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

import beans.DeliveryBeans;
import beans.ItemBeans;
import beans.UserBeans;
import dao.BuyDao;
import dao.BuyDetailDao;

@WebServlet("/ItemBuyConfirm")
public class ItemBuyConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemBuyConfirm() {
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

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemBuyConfirm.jsp");
			dispatcher.forward(request, response);
			return;

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

			//セッションスコープからインスタンスを取得
			@SuppressWarnings("unchecked")
			ArrayList<ItemBeans> itemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");
			DeliveryBeans delivery = (DeliveryBeans) session.getAttribute("delivery");

			//buyテーブル/buy_detailに挿入する変数の準備
			int userID = userSession.getId();
			int deliveryId = delivery.getId();
			int totalPrice = delivery.getPrice();
			ArrayList<Integer> numbers = new ArrayList<Integer>();

			for(ItemBeans item : itemList) {
				//合計金額
				totalPrice += item.getPrice() * item.getNumber();

				//商品個数の数だけ追加
				for(int i = 0 ; i < item.getNumber() ; i++) {
					numbers.add(item.getId());

				}
			}

			//データベース操作
			BuyDao buyDao = new BuyDao();
			//buyテーブルに値を挿入
			int buyId = buyDao.BuyTouroku(userID, totalPrice, deliveryId);


			//buyテーブルのINSERTが成功した場合
			int buyDetailId = 0;
			if(buyId != 0) {

				//データベース操作
				BuyDetailDao buyDetailDao = new BuyDetailDao();
				//buy_detailにテーブルに値を挿入
				buyDetailId = buyDetailDao.BuyDetailTouroku(buyId , numbers);

			}

			//全てのデータベース処理が成功した場合
			if(buyId != 0 && buyDetailId != 0) {

				//全ての処理の後に商品リストと配達方法のセッションスコープを削除
				session.removeAttribute("sItemList");
				session.removeAttribute("delivery");

				//商品購入履歴リストへリダイレクト
				response.sendRedirect("ItemBuyHistoryListServlet");
				return;

			}

			//商品購入履歴リストへリダイレクト
			response.sendRedirect("ItemCartListServlet");
			return;

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

}
