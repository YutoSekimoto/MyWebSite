package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.DBManager;
import beans.CategoryBeans;

public class CategoryDao {

	public ArrayList<CategoryBeans> CategoryList(){

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM category";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//ユーザーリストインスタンス生成
			ArrayList<CategoryBeans> categoryList = new ArrayList<CategoryBeans>();

			//レコードの内容を取得
			while(rs.next()) {

				CategoryBeans category = new CategoryBeans();

				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));

				categoryList.add(category);

			}

			//カテゴリーリストをリターン
			return categoryList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}

	}

}
