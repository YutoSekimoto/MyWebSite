package item;

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
import beans.UserBeans;
import beans.UserBuyBeans;
import dao.BuyDao;
import dao.DeliveryDao;
import dao.UserBuyDao;

@WebServlet("/ItemBuyHistoryListServlet")
public class ItemBuyHistoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemBuyHistoryListServlet() {
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

			//データベース操作
			BuyDao buyDao = new BuyDao();
			//ユーザーIDから商品購入履歴リストを取得
			ArrayList<BuyBeans> buyList = buyDao.BuyList(userSession.getId());

			//データベース操作
			DeliveryDao deliveryDao =  new DeliveryDao();
			//配送IDから配送方法を取得
			for(BuyBeans buy : buyList) {

				//日付をフォーマット
				buy.setFormatDate(buy.getCreateDate());
				//配送方法をセット
				buy.setDeliveryMethod(deliveryDao.DeliveryIdMethod(buy.getDeliveryId()));

			}

			//データベース操作
			UserBuyDao userBuyDao = new UserBuyDao();
			//ユーザーIDからユーザー商品購入履歴リストを取得
			ArrayList<UserBuyBeans> userBuyList = userBuyDao.UserBuyList(userSession.getId());

			//日付をフォーマット
			for(UserBuyBeans userBuy : userBuyList) {

				userBuy.setFormatDate(userBuy.getCreateDate());

			}

			if(buyList != null && userBuyList != null) {//成功

				//リクエストスコープをセット
				request.setAttribute("buyList", buyList);
				request.setAttribute("userBuyList", userBuyList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Item/ItemBuyHistoryList.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//商品リストへリダイレクト
				response.sendRedirect("ItemListServlet");
				return;

			}

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
