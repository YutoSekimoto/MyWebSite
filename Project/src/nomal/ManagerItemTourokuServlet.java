package nomal;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ItemBeans;
import dao.ItemDao;

@WebServlet("/ManagerItemTourokuServlet")
public class ManagerItemTourokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagerItemTourokuServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemTouroku.jsp");
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

			//リクエストパラメータを取得
			String name = request.getParameter("name");
			String detail = request.getParameter("detail");
			String imageFile = request.getParameter("imageFile");
			String price = request.getParameter("price");

			//ゲットパラメータを数値に変換
			int numberPrice = 0;
			//数値への変換
			try {
				numberPrice = Integer.parseInt(price);
			} catch (NumberFormatException nfex) {
				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchList");
				return;
			}

			//未入力を確認
			if(name.equals("") || detail.equals("") || imageFile.equals("")) {

				//エラーメッセーをセット
				request.setAttribute("eM", "未入力の項目があります");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemTouroku.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//登録した商品IDを取得
			int itemId = itemDao.ItemTouroku(name , detail , numberPrice , imageFile);

			if(itemId != 0) {//成功

				//商品IDから商品を取得
				ItemBeans item = itemDao.ItemIdSearch(itemId);

				if(item != null) {//成功

					request.setAttribute("eM" , "商品の登録に成功しました");
					request.setAttribute("item", item);

					//フォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemTouroku.jsp");
					dispatcher.forward(request, response);
					return;

				}

			}else {//失敗

				request.setAttribute("eM" , "商品の登録に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemTouroku.jsp");
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
