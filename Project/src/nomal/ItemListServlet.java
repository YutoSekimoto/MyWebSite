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

import beans.ItemBeans;
import beans.UserBeans;
import dao.ItemDao;

@WebServlet("/ItemListServlet")
public class ItemListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemListServlet() {
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

			//データベース操作
			ItemDao itemDao = new ItemDao();
			ArrayList<ItemBeans>  itemList = itemDao.ItemList();

			//リクエストスコープをセット
			request.setAttribute("itemList", itemList);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemList.jsp");
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

			//リクエストパラーメータを取得
			String name = request.getParameter("name");
			String bottomPrice = request.getParameter("bottomPrice");
			String topPrice = request.getParameter("topPrice");


			//ゲットパラメータを数値に変換
			int numberBottomPrice = 0;
			int numberTopPrice = 0;

			//取得に失敗
			if(!(bottomPrice.equals(""))) {
				//数値への変換
				try {
					numberBottomPrice = Integer.parseInt(bottomPrice);
				} catch (NumberFormatException nfex) {
					//商品リストへリダイレクト
					response.sendRedirect("ItemListServlet");
					return;
				}
			}

			//取得に失敗
			if(!(topPrice.equals(""))) {
				//数値への変換
				try {
					numberTopPrice = Integer.parseInt(topPrice);
				} catch (NumberFormatException nfex) {
					//商品リストへリダイレクト
					response.sendRedirect("ItemListServlet");
					return;
				}
			}

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//検索した商品リストを取得
			ArrayList<ItemBeans> itemList = itemDao.ItemSearchList2(name , numberBottomPrice , numberTopPrice);

			if(!(itemList.isEmpty())) {//成功

				//リクエストスコープにセット
				request.setAttribute("itemList", itemList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemList.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				request.setAttribute("eM", "検索した商品はありませんでした");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemList.jsp");
				dispatcher.forward(request, response);
				return;

			}

		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

}
