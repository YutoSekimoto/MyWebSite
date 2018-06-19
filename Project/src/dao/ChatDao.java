package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import base.DBManager;
import beans.ChatBeans;

public class ChatDao {

	//コメント登録メソッド
	public int CommentTouroku(int itemid , String name , String comment) {

		int id = 0;
		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "INSERT INTO chat (item_id , name , comment , create_date) VALUES (? , ? , ? , ?)";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, itemid);
			pstmt.setString(2, name);
			pstmt.setString(3, comment);
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

	//ユーザー商品IDコメント検索メソッド
	public ArrayList<ChatBeans> CommentList(int itemid) {

		Connection conn = null;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT * FROM chat WHERE item_id = ? ORDER BY id DESC";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemid);

			//PreparedStatementを実行
			ResultSet rs = pstmt.executeQuery();

			//購入リストインスタンス生成
			ArrayList<ChatBeans> chatList = new ArrayList<ChatBeans>();

			//レコードの内容を取得
			while(rs.next()) {

				ChatBeans chat =  new ChatBeans();

				chat.setId(rs.getInt("id"));
				chat.setItemId(rs.getInt("item_id"));
				chat.setName(rs.getString("name"));
				chat.setComment(rs.getString("comment"));
				chat.setCreateDate(rs.getTimestamp("create_date"));

				chatList.add(chat);

			}

			//コメントリストをリターン
			return chatList;

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

	//コメント削除メソッド
	public int Delete(int id) {

		Connection conn = null;
		int record = 0;
		try {

			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "DELETE FROM chat WHERE item_id = ?";

			//PreparedStatementを準備
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			//PreparedStatementを実行
			record = pstmt.executeUpdate();

			//成功した場合
			if(record != 0) {

				return record;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return record;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return record;
				}
			}
		}

		return record;

	}

}
