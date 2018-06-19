package manager;

import java.io.File;
import java.io.IOException;

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

@WebServlet("/ManagerItemDeleteServlet")
public class ManagerItemDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagerItemDeleteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		try {

			//ユーザーセッションスコープの確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//管理者ログインか一般ログインか確認
			if(!(userSession.getLoginId().equals("admin"))) {

				//商品リストへリダイレクト
				response.sendRedirect("ItemListServlet");
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
				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchListServlet");
				return;
			}

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//IDから商品を取得
			ItemBeans item = itemDao.ItemIdSearch(numberId);

			if(item != null) {//成功

				//リクエストスコープにセット
				request.setAttribute("item", item);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Manager/ManagerItemDelete.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchListServlet");
				return;

			}

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

			//ユーザーセッションスコープの確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//管理者ログインか一般ログインか確認
			if(!(userSession.getLoginId().equals("admin"))) {

				//商品リストへリダイレクト
				response.sendRedirect("ItemListServlet");
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
				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchListServlet");
				return;
			}

			//データベース操作
			ItemDao itemDao = new ItemDao();
			//IDから商品を削除
			String message = itemDao.ItemDelete(numberId);

			if(message != null) {//成功

				//ファイルを削除
				File file = new File(getServletContext().getRealPath("img") + "/" + message);
				if (file.exists()){
					file.delete();
				}

				//リクエストスコープにセット
				request.setAttribute("eM", "削除しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Manager/ManagerItemDelete.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//リクエストスコープにセット
				request.setAttribute("eM", "削除に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Manager/ManagerItemDelete.jsp");
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
