package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import base.DBManager;
import beans.DeliveryBeans;

public class DeliveryDao {

	//配送方法メソッド(名前)
	public DeliveryBeans DeliveryMethod(String deliveryName) {

		Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "SELECT * FROM delivery WHERE name = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deliveryName);

            //PreparedStatementを実行
            ResultSet rs = pstmt.executeQuery();

            //成功した場合
           if(rs.next()) {

        	   //インスタンス作成
        	   DeliveryBeans delivery = new DeliveryBeans();

        	   //インスタンスのフィールドに値をセット
        	   delivery.setId(rs.getInt("id"));
        	   delivery.setName(rs.getString("name"));
        	   delivery.setPrice(rs.getInt("price"));

        	   return delivery;

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

	    //配送方法メソッド(ID)
		public String DeliveryIdMethod(int deliveryid) {

			Connection conn = null;
	        try {

	            //データベースへ接続
	            conn = DBManager.getConnection();

	            //SELECT文を準備
	            String sql = "SELECT name FROM delivery WHERE id = ?";

	            //PreparedStatementを準備
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, deliveryid);

	            //PreparedStatementを実行
	            ResultSet rs = pstmt.executeQuery();

	            //成功した場合
	           if(rs.next()) {

	        	   return rs.getString("name");

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

	    //配送方法メソッド(ID)
		public DeliveryBeans DeliveryIdMethod2(int deliveryid) {

			Connection conn = null;
	        try {

	            //データベースへ接続
	            conn = DBManager.getConnection();

	            //SELECT文を準備
	            String sql = "SELECT * FROM delivery WHERE id = ?";

	            //PreparedStatementを準備
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, deliveryid);

	            //PreparedStatementを実行
	            ResultSet rs = pstmt.executeQuery();

	            //成功した場合
	           if(rs.next()) {

	        	   DeliveryBeans delivery = new DeliveryBeans();

	        	   delivery.setId(rs.getInt("id"));
	        	   delivery.setName(rs.getString("name"));
	        	   delivery.setPrice(rs.getInt("price"));

	        	   return delivery;

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
