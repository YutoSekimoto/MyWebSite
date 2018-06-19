package useritem;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.CategoryBeans;
import beans.UserBeans;
import beans.UserItemBeans;
import dao.CategoryDao;
import dao.UserItemDao;

@WebServlet("/UserItemBuySearchListServlet")
public class UserItemBuySearchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserItemBuySearchListServlet() {
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
			CategoryDao categoryDao = new CategoryDao();
			//カテゴリーリストを取得
			ArrayList<CategoryBeans> categoryList = categoryDao.CategoryList();
			//リクエストパラメーターにセット
			request.setAttribute("categoryList", categoryList);

			//データベース操作
			UserItemDao userItemDao = new UserItemDao();
			ArrayList<UserItemBeans>  userItemList = userItemDao.UserItemList();

			//リクエストスコープをセット
			request.setAttribute("userItemList", userItemList);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemBuySearchList.jsp");
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

			//データベース操作
			CategoryDao categoryDao = new CategoryDao();
			//カテゴリーリストを取得
			ArrayList<CategoryBeans> categoryList = categoryDao.CategoryList();
			//リクエストパラメーターにセット
			request.setAttribute("categoryList", categoryList);

			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//リクエストパラーメータを取得
			String name = request.getParameter("name");
			String bottomPrice = request.getParameter("bottomPrice");
			String topPrice = request.getParameter("topPrice");
			String categoryId = request.getParameter("categoryId");


			//ゲットパラメータを数値に変換
			int numberBottomPrice = 0;
			int numberTopPrice = 0;
			int numberCategoryId = 0;

			//取得に失敗
			if(!(bottomPrice.equals(""))) {
				//数値への変換
				try {
					numberBottomPrice = Integer.parseInt(bottomPrice);
				} catch (NumberFormatException nfex) {
					//ユーザー商品購入検索リストへリダイレクト
					response.sendRedirect("UserItemBuySearchListServlet");
					return;
				}
			}

			//取得に失敗
			if(!(topPrice.equals(""))) {
				//数値への変換
				try {
					numberTopPrice = Integer.parseInt(topPrice);
				} catch (NumberFormatException nfex) {
					//ユーザー商品購入検索リストへリダイレクト
					response.sendRedirect("UserItemBuySearchListServlet");
					return;
				}
			}

			//取得に失敗
			if(!(categoryId.equals(""))) {
				//数値への変換
				try {
					numberCategoryId = Integer.parseInt(categoryId);
				} catch (NumberFormatException nfex) {
					//ユーザー商品購入検索リストへリダイレクト
					response.sendRedirect("UserItemBuySearchListServlet");
					return;
				}
			}

			//データベース操作
			UserItemDao userItemDao = new UserItemDao();
			//検索した商品リストを取得
			ArrayList<UserItemBeans> userItemList = userItemDao.UserItemBuySearchList(name, numberBottomPrice, numberTopPrice, numberCategoryId);

			if(!(userItemList.isEmpty())) {//成功

				//リクエストスコープにセット
				request.setAttribute("userItemList", userItemList);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemBuySearchList.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				request.setAttribute("eM", "検索した商品はありませんでした");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemBuySearchList.jsp");
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
