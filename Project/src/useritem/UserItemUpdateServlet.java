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

import beans.UserBeans;
import beans.UserItemBeans;
import dao.UserItemDao;

@WebServlet("/UserItemUpdateServlet")
public class UserItemUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserItemUpdateServlet() {
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
				//ユーザー商品検索リストへリダイレクト
				response.sendRedirect("UserItemSearchListServlet");
				return;
			}

			//データベース操作
			UserItemDao userItemDao = new UserItemDao();
			//IDから商品を取得
			UserItemBeans userItem = userItemDao.UserItemIdSearch(numberId);

			if(userItem != null) {//成功

				//リクエストスコープにセット
				request.setAttribute("userItem", userItem);

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//ユーザー商品検索リストへリダイレクト
				response.sendRedirect("UserItemSearchListServlet");
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

			//ユーザーセッションスコープの有無を確認
			UserBeans userSession = (UserBeans)session.getAttribute("usersession");
			if(userSession == null) {

				//ログイン画面へリダイレクト
				response.sendRedirect("LoginServlet");
				return;

			}

			//リクエストパラメーターの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			//変数の初期化
			String name = "";
			String detail = "";
			String imageFile = "";
			String price = "";
			int numberPrice = 0;
			String id = "";
			int numberId = 0;

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

					case "id":
						id = item.getString("UTF-8");
						//数値への変換
						try {
							numberId = Integer.parseInt(id);
						} catch (NumberFormatException nfex) {
							//ユーザー商品検索リストへリダイレクト
							response.sendRedirect("UserItemSearchListServlet");
							return;
						}
						break;

					}

				}

			}

			//未入力項目の確認
			if(name.equals("") || detail.equals("") || price.equals("") || imageFile.equals("")) {

				//データベース操作
				UserItemDao userItemDao = new UserItemDao();
				//IDから商品を取得
				UserItemBeans userItem = userItemDao.UserItemIdSearch(numberId);

				//リクエストスコープにセット
				request.setAttribute("userItem", userItem);

				//エラーメッセーをセット
				request.setAttribute("eM", "未入力の項目があります");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//データベース操作
			UserItemDao userItemDao = new UserItemDao();
			//商品を更新
			String message = userItemDao.UserItemUpdate(numberId, name, detail, numberPrice, imageFile);

			if(message != null) {//成功

				//ファイルを削除
				File file = new File(getServletContext().getRealPath("img") + "/" + message);
				if (file.exists()){
					file.delete();
				}

				//エラーメッセージをセット
				request.setAttribute("rM", "更新しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemUpdate.jsp");
				dispatcher.forward(request, response);
				return;

			}else {//失敗

				//エラーメッセージをセット
				request.setAttribute("rM", "更新に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserItem/UserItemUpdate.jsp");
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
