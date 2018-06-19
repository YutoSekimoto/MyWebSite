package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import base.DBManager;
import beans.UserItemBeans;

public class UserItemDao {

	//ユーザー商品登録メソッド
	public int UserItemTouroku(String name , String detail , int price , String imagefile , int categoryid , int userid) {

		int id = 0;
		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "INSERT INTO user_item(name , detail , price , file , category_id , user_id) VALUES (? , ? , ? , ? , ? , ?)";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, name);
			pstmt.setString(2, detail);
			pstmt.setInt(3, price);
			pstmt.setString(4, imagefile);
			pstmt.setInt(5, categoryid);
			pstmt.setInt(6, userid);

			//PreparedStatementを実行
			pstmt.executeUpdate();

			//挿入結果のID自動生成キーを取得
			ResultSet rs = pstmt.getGeneratedKeys();

			//成功
			if(rs.next()) {

				//自動生成キーを返す
				id = rs.getInt(1);
				return id;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return id;
				}
			}
		}

		return id;

	}

	//ユーザー商品ID検索メソッド
	public UserItemBeans UserItemIdSearch(int id) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM user_item WHERe id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//成功した場合
			if(rs.next()) {

				//ユーザー商品インスタンス作成
				UserItemBeans userItem = new UserItemBeans();

				//データベースから全ての値を取得
				userItem.setId(rs.getInt("id"));
				userItem.setName(rs.getString("name"));
				userItem.setDetail(rs.getString("detail"));
				userItem.setPrice(rs.getInt("price"));
				userItem.setFile(rs.getString("file"));
				userItem.setCategoryId(rs.getInt("category_id"));
				userItem.setUserId(rs.getInt("user_id"));

				return userItem;

			}

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

		return null;

	}

	//全てのユーザー商品ID検索メソッド
	public ArrayList<UserItemBeans> UserItemIdList(int id) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM user_item WHERE user_id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//ユーザー商品リストインスタンス作成
			ArrayList<UserItemBeans> userItemList = new ArrayList<UserItemBeans>();

			//成功した場合
			while(rs.next()) {

				//ユーザー商品インスタンス作成
				UserItemBeans userItem = new UserItemBeans();

				//データベースから全ての値を取得
				userItem.setId(rs.getInt("id"));
				userItem.setName(rs.getString("name"));
				userItem.setDetail(rs.getString("detail"));
				userItem.setPrice(rs.getInt("price"));
				userItem.setFile(rs.getString("file"));
				userItem.setCategoryId(rs.getInt("category_id"));
				userItem.setUserId(rs.getInt("user_id"));

				userItemList.add(userItem);

			}

			return userItemList;

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

	//全てのユーザー商品検索メソッド
	public ArrayList<UserItemBeans> UserItemList() {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM user_item";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//ユーザー商品リストインスタンス作成
			ArrayList<UserItemBeans> userItemList = new ArrayList<UserItemBeans>();

			//成功した場合
			while(rs.next()) {

				//ユーザー商品インスタンス作成
				UserItemBeans userItem = new UserItemBeans();

				//データベースから全ての値を取得
				userItem.setId(rs.getInt("id"));
				userItem.setName(rs.getString("name"));
				userItem.setDetail(rs.getString("detail"));
				userItem.setPrice(rs.getInt("price"));
				userItem.setFile(rs.getString("file"));
				userItem.setCategoryId(rs.getInt("category_id"));
				userItem.setUserId(rs.getInt("user_id"));

				userItemList.add(userItem);

			}

			return userItemList;

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

	//ユーザー商品リスト検索メソッド
	public ArrayList<UserItemBeans> UserItemSearchList(String name , int price , String imagefile , int categoryid) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM user_item WHERE price >= 0";

			//条件式の追加s
			if(!(name.equals(""))) {
				sql += " AND name LIKE '%" + name + "%'";
				sql += " OR name = '" + name + "'";
			}
			if(!(price == 0)) {
				sql += " AND price =" + price;
			}
			if(!(imagefile.equals(""))) {
				sql += " AND file =" + "'" + imagefile + "'";
			}
			if(!(categoryid == 0)) {
				sql += " AND category_id =" + categoryid;
			}

			//SELECTを実行し、結果表を取得
			Statement stmt = conn.createStatement();

			//Statementを実行
			ResultSet rs = stmt.executeQuery(sql);

			//ユーザー商品リストインスタンス作成
			ArrayList<UserItemBeans> userItemList = new ArrayList<UserItemBeans>();

			//成功した場合
			while(rs.next()) {

				//ユーザー商品インスタンス作成
				UserItemBeans userItem = new UserItemBeans();

				//データベースから全ての値を取得
				userItem.setId(rs.getInt("id"));
				userItem.setName(rs.getString("name"));
				userItem.setDetail(rs.getString("detail"));
				userItem.setPrice(rs.getInt("price"));
				userItem.setFile(rs.getString("file"));
				userItem.setCategoryId(rs.getInt("category_id"));
				userItem.setUserId(rs.getInt("user_id"));

				userItemList.add(userItem);

			}

			return userItemList;

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

	//ユーザー商品リスト購入検索メソッド
	public ArrayList<UserItemBeans> UserItemBuySearchList(String name , int bottomprice , int topprice , int categoryid) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM user_item WHERE price >= 0";

			//条件式の追加
			if(!(name.equals(""))) {
				sql += " AND name LIKE '%" + name + "%'";
				sql += " OR name = '" + name + "'";
			}
			if(!(bottomprice == 0)) {
				sql += " AND price >=" + bottomprice;
			}
			if(!(topprice == 0)) {
				sql += " AND price <=" + topprice;
			}
			if(!(categoryid == 0)) {
				sql += " AND category_id =" + categoryid;
			}


			//SELECTを実行し、結果表を取得
			Statement stmt = conn.createStatement();

			//Statementを実行
			ResultSet rs = stmt.executeQuery(sql);

			//ユーザー商品リストインスタンス作成
			ArrayList<UserItemBeans> userItemList = new ArrayList<UserItemBeans>();

			//成功した場合
			while(rs.next()) {

				//ユーザー商品インスタンス作成
				UserItemBeans userItem = new UserItemBeans();

				//データベースから全ての値を取得
				userItem.setId(rs.getInt("id"));
				userItem.setName(rs.getString("name"));
				userItem.setDetail(rs.getString("detail"));
				userItem.setPrice(rs.getInt("price"));
				userItem.setFile(rs.getString("file"));
				userItem.setCategoryId(rs.getInt("category_id"));
				userItem.setUserId(rs.getInt("user_id"));

				userItemList.add(userItem);

			}

			return userItemList;

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

	//ユーザー商品削除メソッド
	public String UserItemDelete(int id) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql1 = "SELECT file FROM user_item WHERE id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setInt(1, id);

			//PreparedStatementを実行
			ResultSet rs = pstmt1.executeQuery();

			String fileName = null;
			//成功した場合
			if(rs.next()) {

				//ファイル名を取得
				fileName = rs.getString("file");

			}

			//SELECT文を準備
			String sql2 = "DELETE FROM user_item WHERE id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, id);

			//PreparedStatementを実行
			int records = pstmt2.executeUpdate();

			//成功した場合
			if(records != 0) {

				//ファイル名をリターン
				return fileName;

			}

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

		return null;

	}

	//商品更新メソッド
	public String UserItemUpdate(int id , String name , String detail , int price , String imagefile) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql1 = "SELECT file FROM user_item WHERE id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setInt(1, id);

			//PreparedStatementを実行
			ResultSet rs = pstmt1.executeQuery();

			String fileName = null;
			//成功した場合
			if(rs.next()) {

				//ファイル名を取得
				fileName = rs.getString("file");

			}

			//SELECT文を準備
			String sql2 = "UPDATE user_item SET name = ? , detail = ? , price = ? , file = ? WHERE id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, name);
			pstmt2.setString(2, detail);
			pstmt2.setInt(3, price);
			pstmt2.setString(4, imagefile);
			pstmt2.setInt(5, id);

			//PreparedStatementを実行
			int records = pstmt2.executeUpdate();

			//成功した場合
			if(records != 0) {

				return fileName;

			}

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

		return null;

	}

}
