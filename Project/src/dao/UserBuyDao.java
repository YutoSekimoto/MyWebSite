package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import base.DBManager;
import beans.UserBuyBeans;

public class UserBuyDao {

	//ユーザー購入登録メソッド
	public int UserBuyTouroku(String itemname , int userid , String imagefile , int price) {

		int id = 0;
		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "INSERT INTO user_buy (item_name , user_id , file , price , create_date) VALUES (? , ? , ? , ? , ?)";

			//PreparedStatementを準備/ID取得
			PreparedStatement pstmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, itemname);
			pstmt.setInt(2, userid);
			pstmt.setString(3, imagefile);
			pstmt.setInt(4, price);
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

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

		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//失敗した場合
		return id;

	}

	//ユーザー購入ユーザーID検索メソッド
	public ArrayList<UserBuyBeans> UserBuyList(int userid) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM user_buy WHERE user_id = ? ORDER BY id DESC";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//ユーザー購入リストインスタンス生成
			ArrayList<UserBuyBeans> userBuyList = new ArrayList<UserBuyBeans>();

			//レコードの内容を取得
			while(rs.next()) {

				UserBuyBeans userBuy = new UserBuyBeans();

				userBuy.setId(rs.getInt("id"));
				userBuy.setItemName(rs.getString("item_name"));
				userBuy.setUserId(rs.getInt("user_id"));
				userBuy.setImageFile(rs.getString("file"));
				userBuy.setPrice(rs.getInt("price"));
				userBuy.setCreateDate(rs.getTimestamp("create_date"));

				userBuyList.add(userBuy);

			}

			//ユーザー購入リストをリターン
			return userBuyList;

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
