package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import base.DBManager;
import beans.BuyBeans;

public class BuyDao {

	//購入登録メソッド
	public int BuyTouroku(int userid , int price , int deliveryid) {

		int id = 0;
		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "INSERT INTO buy (user_id , price , delivery_id , create_date) VALUES (? , ? , ? , ?)";

			//PreparedStatementを準備/ID取得
			PreparedStatement pstmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, userid);
			pstmt.setInt(2, price);
			pstmt.setInt(3, deliveryid);
			pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

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

	//全ユーザーリストメソッド
	public ArrayList<BuyBeans> BuyList(int userid) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM buy WHERE user_id = ? ORDER BY id DESC";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//ユーザーリストインスタンス生成
			ArrayList<BuyBeans> buyList = new ArrayList<BuyBeans>();

			//レコードの内容を取得
			while(rs.next()) {

				BuyBeans buy = new BuyBeans();

				buy.setId(rs.getInt("id"));
				buy.setUseId(rs.getInt("user_id"));
				buy.setPrice(rs.getInt("price"));
				buy.setDeliveryId(rs.getInt("delivery_id"));
				buy.setCreateDate(rs.getTimestamp("create_date"));

				buyList.add(buy);

			}

			//ユーザーリストをリターン
			return buyList;

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

	//全ユーザーリストメソッド
	public BuyBeans BuyId(int id) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM buy WHERE id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//レコードの内容を取得
			if(rs.next()) {

				BuyBeans buy = new BuyBeans();

				buy.setId(rs.getInt("id"));
				buy.setUseId(rs.getInt("user_id"));
				buy.setPrice(rs.getInt("price"));
				buy.setDeliveryId(rs.getInt("delivery_id"));
				buy.setCreateDate(rs.getTimestamp("create_date"));

				return buy;

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
