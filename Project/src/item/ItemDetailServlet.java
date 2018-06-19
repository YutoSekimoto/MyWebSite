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

import beans.ItemBeans;
import beans.UserBeans;
import dao.ItemDao;

@WebServlet("/ItemDetailServlet")
public class ItemDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemDetailServlet() {
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

			//ゲットパラメータを取得
			String id = request.getParameter("id");

			//ゲットパラメータを数値に変換
			int numberId = 0;
			//数値への変換
			try {
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//商品リストへリダイレクト
				response.sendRedirect("ItemListServlet");
				return;
			}

			//データベース操作
			ItemDao itemDao =  new ItemDao();
			//IDから商品を取得
			ItemBeans item = itemDao.ItemIdSearch(numberId);

			//リクエストスコープにセット
			request.setAttribute("item", item);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Item/ItemDetail.jsp");
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

			//リクエストパラメータを取得
			String id = request.getParameter("id");
			String number = request.getParameter("number");

			if(!(id.equals("")) && !(number.equals(""))) {

				//ゲットパラメータを数値に変換
				int numberId = 0;
				int numberNumber = 0;

				//取得に失敗
				if(!(id.equals("")) && !(number.equals(""))) {

					//数値への変換
					try {
						numberId = Integer.parseInt(id);
						numberNumber = Integer.parseInt(number);
					} catch (NumberFormatException nfex) {
						//商品リストへリダイレクト
						response.sendRedirect("ItemListServlet");
						return;
					}

				}

				//データベース操作
				ItemDao itemDao = new ItemDao();
				//IDから商品を取得
				ItemBeans Item = itemDao.ItemIdSearch(numberId);

				//購入個数をセット
				Item.setNumber(numberNumber);
				//ユーザーIDをセット
				Item.setUserId(userSession.getId());

				//商品カートセッションを取得
				@SuppressWarnings("unchecked")
				ArrayList<ItemBeans> sItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");

				//商品カートセッションがnullの場合
				if(sItemList == null) {

					//商品カートセッションススコープに保存するコレクションインスタンスを作成
					sItemList = new ArrayList<ItemBeans>();

				}

				//商品カートに既に同じ商品が入っているのか確認
				for(ItemBeans sItem : sItemList) {

					//商品カートに既に同じ商品が入っているのか、商品カートのユーザーIDがここから

					if(sItem.getId() == Item.getId() && sItem.getUserId() == userSession.getId()) {

						//個数を追加
						sItem.setNumber(sItem.getNumber() + Item.getNumber());

						//商品カートリストへリダイレクト
						response.sendRedirect("ItemCartListServlet");
						return;

					}

				}

				//商品カートに商品を追加
				sItemList.add(Item);

				//セッションスコープにインスタンスを保存
				session.setAttribute("sItemList", sItemList);

				//商品カートリストへリダイレクト
				response.sendRedirect("ItemCartListServlet");

			}else {

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

}
