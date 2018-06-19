package useritem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.CategoryBeans;
import beans.UserBeans;
import beans.UserItemBeans;
import dao.CategoryDao;
import dao.UserItemDao;

@WebServlet("/UserItemTourokuServlet")
public class UserItemTourokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserItemTourokuServlet() {
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

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemTouroku.jsp");
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

			//ユーザーセッションスコープの確認
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

			//変数の初期化
			String name = "";
			String detail = "";
			String imageFile = "";
			String price = "";
			int numberPrice = 0;
			String categoryId = "";
			int numberCategoryId = 0;
			int userId = userSession.getId();

			//DiskFileItemFactory/ServletFileUploadインスタンスの取得
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			//リクエストパラメーターをFileItemコレクションに保存
			ArrayList<FileItem> items;
			try {
				items = (ArrayList<FileItem>) upload.parseRequest(request);
			} catch (FileUploadException e) {
				//ユーザー商品検索リストへリダイレクト
				response.sendRedirect("UserItemSearchListServlet");
				return;
			}

			//FileItemコレクションの処理
			for (FileItem item : items) {

				if(!(item.isFormField())) {//画像ファイルの処理
					try {

						imageFile = item.getName();
						item.write(new File(getServletContext().getRealPath("img") + "/" + item.getName()));

					} catch (Exception e) {
						//ユーザー商品検索リストへリダイレクト
						response.sendRedirect("UserItemSearchListServlet");
						return;
					}

				}else {//画像ファイル以外の処理

					switch(item.getFieldName()){

					case "name":
						name = item.getString("UTF-8");
						break;

					case "detail":
						detail = item.getString("UTF-8");
						break;

					case "price":
						price = item.getString("UTF-8");
						//数値への変換
						try {
							numberPrice = Integer.parseInt(price);
						} catch (NumberFormatException nfex) {
							//ユーザー商品検索リストへリダイレクト
							response.sendRedirect("UserItemSearchListServlet");
							return;
						}
						break;

					case "categoryId":
						categoryId = item.getString("UTF-8");
						//数値への変換
						try {
							numberCategoryId = Integer.parseInt(categoryId);
						} catch (NumberFormatException nfex) {
							//ユーザー商品検索リストへリダイレクト
							response.sendRedirect("UserItemSearchListServlet");
							return;
						}
						break;

					}

				}

			}

			//未入力を確認
			if(name.equals("") || detail.equals("") || imageFile.equals("")) {

				//エラーメッセーをセット
				request.setAttribute("eM", "未入力の項目があります");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemTouroku.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//データベース操作
			UserItemDao userItemDao = new UserItemDao();
			//登録した商品IDを取得
			int userItemId = userItemDao.UserItemTouroku(name, detail, numberPrice, imageFile, numberCategoryId, userId);

			if(userItemId != 0) {//成功

				//商品IDから商品を取得
				UserItemBeans userItem = userItemDao.UserItemIdSearch(userItemId);

				if(userItem != null) {//成功

					request.setAttribute("eM" , "商品の登録に成功しました");
					request.setAttribute("userItem", userItem);

					//フォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemTouroku.jsp");
					dispatcher.forward(request, response);
					return;

				}

			}else {//失敗

				request.setAttribute("eM" , "商品の登録に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemTouroku.jsp");
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
