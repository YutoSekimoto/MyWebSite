package nomal;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ItemBeans;
import dao.ItemDao;

@WebServlet("/ManagerItemSearchListServlet")
public class ManagerItemSearchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagerItemSearchListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//全ての商品リストを取得
			ArrayList<ItemBeans> itemList = itemDao.ItemList();

			//リクエストスコープにセット
			request.setAttribute("itemList", itemList);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemSearchList.jsp");
			dispatcher.forward(request, response);
			return;


		}catch (Exception e) {
			//エラーページへリダイレクト
			response.sendRedirect("ErrorServlet");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//リクエストパラーメータを取得
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String imageFile = request.getParameter("imagefile");

			//ゲットパラメータを数値に変換
			int numberPrice = 0;

			//価格入力確認
			if(!(price.equals(""))) {

				//数値への変換
				try {
					numberPrice = Integer.parseInt(price);
				} catch (NumberFormatException nfex) {
					//管理者商品リストへリダイレクト
					response.sendRedirect("ManagerItemSearchListServlet");
					return;
				}

			}

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//検索した商品リストを取得
			ArrayList<ItemBeans> itemList = itemDao.ItemSearchList(name , numberPrice , imageFile);

			if(!(itemList.isEmpty())) {//成功

				//リクエストスコープにセット
				request.setAttribute("itemList", itemList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemSearchList.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				request.setAttribute("eM", "検索した商品はありませんでした");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemSearchList.jsp");
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
