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

@WebServlet("/ManagerItemUpdateServlet")
public class ManagerItemUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagerItemUpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//ゲットパラメータを取得
			String id = request.getParameter("id");

			//ゲットパラメータを数値に変換
			int numberId = 0;
			//数値への変換
			try {
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchListServlet");
				return;
			}

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//IDから商品を取得
			ItemBeans item = itemDao.ItemIdSearch(numberId);

			//リクエストスコープにセット
			request.setAttribute("item", item);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemUpdate.jsp");
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
			String detail = request.getParameter("detail");
			String price = request.getParameter("price");
			String imageFile = request.getParameter("imagefile");
			String id = request.getParameter("id");

			//取得に失敗
			if(price.equals("") || id.equals("")) {

				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchListServlet");
				return;

			}

			//ゲットパラメータを数値に変換
			int numberPrice = 0;
			int numberId = 0;
			//数値への変換
			try {
				numberPrice = Integer.parseInt(price);
				numberId = Integer.parseInt(id);
			} catch (NumberFormatException nfex) {
				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchListServlet");
				return;
			}

			//未入力項目の確認
			if(name.equals("") || detail.equals("") || price.equals("") || imageFile.equals("")) {

				//データベース操作
				ItemDao itemDao = new ItemDao();
				//IDから商品を取得
				ItemBeans item = itemDao.ItemIdSearch(numberId);

				//リクエストスコープにセット
				request.setAttribute("item", item);

				//エラーメッセーをセット
				request.setAttribute("eM", "未入力の項目があります");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//商品を更新
			String message = itemDao.ItemUpdate(numberId, name, detail, numberPrice, imageFile);

			if(message != null) {//成功

				//エラーメッセージをセット
				request.setAttribute("rM", "更新しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//エラーメッセージをセット
				request.setAttribute("rM", "更新に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ManagerItemUpdate.jsp");
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
