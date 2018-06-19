package item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
import dao.DeliveryDao;

@WebServlet("/ItemCartListServlet")
public class ItemCartListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemCartListServlet() {
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Item/ItemCartList.jsp");
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

			//ゲットパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//数値変換用IDを用意
			int numberId  = 0;
			//削除フォーム
			if(request.getParameter("delete") != null && !(request.getParameter("id").equals(""))) {

				//ゲットパラメータを取得
				String id = request.getParameter("id");

				//数値への変換
				try {
					numberId = Integer.parseInt(id);
				} catch (NumberFormatException nfex) {
					//商品リストへリダイレクト
					response.sendRedirect("ItemListServlet");
					return;
				}

				//セッションスコープからインスタンスを取得
				@SuppressWarnings("unchecked")
				ArrayList<ItemBeans> sItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");

				//商品カートリストセッションスコープの確認
				if(sItemList != null) {

					//インスタンス生成
					Iterator<ItemBeans> iItemList = sItemList.iterator();

					//該当要素の削除
					while(iItemList.hasNext()) {

						ItemBeans iItem = iItemList.next();

						if(iItem.getId() == numberId) {
							iItemList.remove();

							//要素がなければセッションスコープを削除
							if(sItemList.isEmpty()) {
								session.removeAttribute("sItemList");
							}

							//商品カートリストへリダイレクト
							response.sendRedirect("ItemCartListServlet");
							return;
						}

					}

				}

			}

			//配送フォーム
			if(request.getParameter("deliveryName") != null && !(request.getParameter("deliveryName").equals(""))) {

				//ゲットパラメータを取得
				String deliveryName = request.getParameter("deliveryName");

				//データベース操作
				DeliveryDao deliveryDao = new DeliveryDao();
				DeliveryBeans delivery = deliveryDao.DeliveryMethod(deliveryName);

				//ユーザーIDを配送インスタンスにセット
				delivery.setUserId(userSession.getId());

				//配送コレクションインスタンスに配送インスタンスをセット
				ArrayList<DeliveryBeans> deliveryList = new ArrayList<DeliveryBeans>();
				deliveryList.add(delivery);

				//セッションスコープにインスタンスを保存
				session.setAttribute("deliveryList", deliveryList);

				//商品カートリストへリダイレクト
				response.sendRedirect("ItemCartListServlet");
				return;

			}

			//個数フォーム
			if(request.getParameter("number") != null && !(request.getParameter("number").equals("")) && request.getParameter("itemId") != null && !(request.getParameter("itemId").equals(""))) {

				//ゲットパラメータを取得
				String number = request.getParameter("number");
				String itemId = request.getParameter("itemId");

				//ゲットパラメータを数値に変換
				int numberNumber = 0;
				int numberItemId = 0;

				//数値への変換
				try {
					numberNumber = Integer.parseInt(number);
					numberItemId = Integer.parseInt(itemId);
				} catch (NumberFormatException nfex) {
					//商品リストへリダイレクト
					response.sendRedirect("ItemListServlet");
					return;
				}

				//商品カートセッションを取得
				@SuppressWarnings("unchecked")
				ArrayList<ItemBeans> sItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");

				//商品カートセッションがnullの場合
				if(sItemList == null) {

					//商品リストへリダイレクト
					response.sendRedirect("ItemListServlet");
					return;

				}

				//商品カートに既に同じ商品が入っているのか確認
				for(ItemBeans sItem : sItemList) {

					//商品カートに既に同じ商品が入っているのか場合
					if(sItem.getId() == numberItemId && sItem.getUserId() == userSession.getId()) {

						//個数を変更
						sItem.setNumber(numberNumber);

						//商品カートリストへリダイレクト
						response.sendRedirect("ItemCartListServlet");
						return;

					}

				}

			}

			//商品リストへリダイレクト
			response.sendRedirect("ItemListServlet");
			return;

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

}
