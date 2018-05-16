package nomal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.DeliveryBeans;
import beans.ItemBeans;
import dao.DeliveryDao;

/**
 * Servlet implementation class ItemCartListServlet
 */
@WebServlet("/ItemCartListServlet")
public class ItemCartListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemCartListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemCartList.jsp");
	    dispatcher.forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		//レスポンス
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		//リクエストパラメーターの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		//削除フォーム
		if(request.getParameter("delete") != null && !(request.getParameter("id").equals(""))) {

			//ID文字列を数値に変換
			int numberId = Integer.parseInt(request.getParameter("id"));

			//HttpSessionインスタンスの取得
			HttpSession session = request.getSession();

			//セッションスコープからインスタンスを取得
			ArrayList<ItemBeans> sItemList = (ArrayList<ItemBeans>) session.getAttribute("sItemList");

			//インスタンス生成
			Iterator<ItemBeans> iItemList = sItemList.iterator();

			//該当要素の削除
			while(iItemList.hasNext()) {

				ItemBeans iItem = iItemList.next();

				if(iItem.getId() == numberId) {
					iItemList.remove();

					//要素がなければセッションスコープを削除
					if(sItemList.isEmpty()) {
						session.removeAttribute("sItemList");
					}

					//商品カートリストへリダイレクト
					response.sendRedirect("ItemCartListServlet");
					return;
				}

			}
		}

		//配送フォーム
		if(request.getParameter("delivery") != null && !(request.getParameter("deliveryName").equals(""))) {

			//リクエストパラーメータを取得
			String deliveryName = request.getParameter("deliveryName");
			out.println(deliveryName);

			//データベース操作
			DeliveryDao deliveryDao = new DeliveryDao();
			DeliveryBeans delivery = deliveryDao.DeliveryMethod(deliveryName);

			//HttpSessionインスタンスの取得
			HttpSession session = request.getSession();

			//セッションスコープにインスタンスを保存
			session.setAttribute("delivery", delivery);

			//商品カートリストへリダイレクト
			response.sendRedirect("ItemCartListServlet");
			return;

		}

	}

}
