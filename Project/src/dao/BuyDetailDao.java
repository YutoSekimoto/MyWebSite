package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import base.DBManager;
import beans.ItemBeans;

public class BuyDetailDao {

	//購入詳細登録メソッド
	public int BuyDetailTouroku(int buyid , ArrayList<Integer> itemListId) {

		int id = 0;
		int number1 = 0;
		int number2 = 0;
		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "INSERT INTO buy_detail (buy_id , item_id) VALUES (? , ?)";

			//sql文を追加
			for(@SuppressWarnings("unused") int itemId : itemListId) {

				if(number1 == 0) {
					number1 += 1;
					continue;
				}else {
					sql += " , (? , ?)";
				}

			}

			//PreparedStatementを準備/ID取得
			PreparedStatement pstmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);

			//sql文にセット
			for(int itemId : itemListId) {

				if(number2 == 0) {

					pstmt.setInt(1, buyid);
					pstmt.setInt(2, itemId);

				}else {

					pstmt.setInt(number2 + 1, buyid);
					pstmt.setInt(number2 + 2, itemId);

				}

				number2 += 2;

			}

			//PreparedStatementを実行
			pstmt.executeUpdate();

			//挿入結果のID自動生成キーを取得
			ResultSet rs = pstmt.getGeneratedKeys();

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
		return id;
	}

	//購入商品ID取得メソッド
	public ArrayList<ItemBeans> BuyItemIdList(int id) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT item_id , count(*) AS count FROM buy_detail WHERE buy_id = ? GROUP BY item_id ORDER BY item_id;";

			//PreparedStatementを準備/ID取得
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//整数型コレクションインスタンス作成
			ArrayList<ItemBeans> itemList = new ArrayList<ItemBeans>();

			//商品IDを商品IDリストに保存
			while(rs.next()) {

				ItemBeans item = new ItemBeans();

				item.setId(rs.getInt("item_id"));
				item.setNumber(rs.getInt("count"));

				itemList.add(item);

			}

			return itemList;


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
		return null;

	}

}
