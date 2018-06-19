package manager;

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

import beans.ItemBeans;
import beans.UserBeans;
import dao.ItemDao;

@WebServlet("/ManagerItemTourokuServlet")
public class ManagerItemTourokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagerItemTourokuServlet() {
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

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Manager/ManagerItemTouroku.jsp");
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

			//管理者ログインか一般ログインか確認
			if(!(userSession.getLoginId().equals("admin"))) {

				//商品リストへリダイレクト
				response.sendRedirect("ItemListServlet");
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

			//DiskFileItemFactory/ServletFileUploadインスタンスの取得
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			//リクエストパラメーターをFileItemコレクションに保存
			ArrayList<FileItem> items;
			try {
				items = (ArrayList<FileItem>) upload.parseRequest(request);
			} catch (FileUploadException e) {
				//管理者商品リストへリダイレクト
				response.sendRedirect("ManagerItemSearchListServlet");
				return;
			}

			//FileItemコレクションの処理
			for (FileItem item : items) {

				if(!(item.isFormField())) {//画像ファイルの処理
					try {

						imageFile = item.getName();
						item.write(new File(getServletContext().getRealPath("img") + "/" + item.getName()));

					} catch (Exception e) {
						//管理者商品リストへリダイレクト
						response.sendRedirect("ManagerItemSearchListServlet");
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
							//管理者商品リストへリダイレクト
							response.sendRedirect("ManagerItemSearchListServlet");
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
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Manager/ManagerItemTouroku.jsp");
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
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Manager/ManagerItemTouroku.jsp");
					dispatcher.forward(request, response);
					return;

				}

			}else {//失敗

				request.setAttribute("eM" , "商品の登録に失敗しました");

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Manager/ManagerItemTouroku.jsp");
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
