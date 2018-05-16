package nomal;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.DeliveryBeans;
import beans.ItemBeans;
import dao.BuyDao;
import dao.BuyDetailDao;

/**
 * Servlet implementation class ItemBuyConfirm
 */
@WebServlet("/ItemBuyConfirm")
public class ItemBuyConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemBuyConfirm() {
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
		HttpSession session  = request.getSession();

		//セッションスコープにインスタンスを保存
		int userId = 1;
		session.setAttribute("userId", userId);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemBuyConfirm.jsp");
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

		//キャンセルの場合
		if(request.getParameter("cancel") != null) {

			//商品カートリストへリダイレクト
			response.sendRedirect("ItemCartListServlet");

		}

		//HttpSessionインスタンの取得
		HttpSession session = request.getSession();

		//セッションスコープからインスタンスを取得
		ArrayList<ItemBeans> itemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");
		DeliveryBeans delivery = (DeliveryBeans) session.getAttribute("delivery");

		//buyテーブルに挿入する変数の準備
		//ユーザーID
		int userID = 1;
		//配送ID
		int deliveryId = delivery.getId();
		//現在時刻
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayC = format.format(today);
        //合計金額
        int totalPrice = delivery.getPrice();
        //アイテムID
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(ItemBeans item : itemList) {
			totalPrice += item.getPrice() * item.getNumber();

			//商品個数の数だけ追加
			for(int i = 0 ; i < item.getNumber() ; i++) {
				numbers.add(item.getId());
			}

		}

		//データベース操作/buyテーブルに値を挿入
		BuyDao buyDao = new BuyDao();
		int id = buyDao.BuyTouroku(userID, totalPrice, deliveryId, todayC);

		//buyテーブルのINSERTが成功した場合
		if(id != 0) {

			//データベース操作/buy_detailにテーブルに値を挿入
			BuyDetailDao buyDetailDao = new BuyDetailDao();
			buyDetailDao.BuyDetailTouroku(id , numbers);

		}

		//全ての処理の後に商品リストと配達方法のセッションスコープを削除
		session.removeAttribute("sItemList");
		session.removeAttribute("delivery");

		//商品購入履歴リストへリダイレクト
		response.sendRedirect("ItemBuyHistoryListServlet");

	}

}
