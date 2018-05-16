package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import base.DBManager;
import beans.ItemBeans;

public class ItemDao {

	//商品登録メソッド
    public ItemBeans ItemTouroku(String name , String detail , int price , String imagefile) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "INSERT INTO item (name , detail , price , file) VALUES (? , ? , ? , ?)";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, detail);
            pstmt.setInt(3, price);
            pstmt.setString(4, imagefile);

            //PreparedStatementを実行
            int records = pstmt.executeUpdate();

            //成功した場合
            if(records != 0) {

            	return new ItemBeans(name , detail , price , imagefile);

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

    //商品リスト検索メソッド
    public ArrayList<ItemBeans> ItemList() {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "SELECT * FROM item";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //PreparedStatementを実行
            ResultSet rs = pstmt.executeQuery();


            //商品リストインスタンス作成
            ArrayList<ItemBeans> itemList = new ArrayList<ItemBeans>();

            //成功した場合
            while(rs.next()) {

            	//商品インスタンス作成
            	ItemBeans item = new ItemBeans();

            	//データベースから全ての値を取得
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setDetail(rs.getString("detail"));
            item.setPrice(rs.getInt("price"));
            	item.setFile(rs.getString("file"));

            	//商品リストに商品を追加
            	itemList.add(item);

            	}

            return itemList;

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

    //商品リスト検索メソッド
    public ArrayList<ItemBeans> ItemSearchList(String name , String detail , int price , String imagefile) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "SELECT * FROM item WHERE price >= 0 ";

            //条件式の追加
            if(!(name.equals(""))) {
            	sql += "AND name = '" + name + "'";
            }
            if(!(detail.equals(""))) {
            	sql += "AND detail =" + "'" + detail + "'";
            }
            if(!(price == 0)) {
            	sql += "AND price =" + price;
            }
            if(!(imagefile.equals(""))) {
            	sql += "AND file =" + "'" + imagefile + "'";
            }

            // SELECTを実行し、結果表を取得
            Statement stmt = conn.createStatement();

            //Statementを実行
            ResultSet rs = stmt.executeQuery(sql);

            //商品リストインスタンス作成
            ArrayList<ItemBeans> itemList = new ArrayList<ItemBeans>();

            //成功した場合
            while(rs.next()) {

            	//商品インスタンス作成
            	ItemBeans item = new ItemBeans();

            	//データベースから全ての値を取得
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setDetail(rs.getString("detail"));
            item.setPrice(rs.getInt("price"));
            	item.setFile(rs.getString("file"));

            	//商品リストに商品を追加
            	itemList.add(item);

            	}

            return itemList;

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

    //商品ID検索メソッド
    public ItemBeans ItemIdSearch(int id) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "SELECT * FROM item WHERE id = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            //PreparedStatementを実行
            ResultSet rs = pstmt.executeQuery();

            //成功した場合
            if(rs.next()) {

            	//商品インスタンス作成
            	ItemBeans item = new ItemBeans();

            	//データベースから全ての値を取得
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setDetail(rs.getString("detail"));
            item.setPrice(rs.getInt("price"));
            	item.setFile(rs.getString("file"));

            	return item;

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

    //商品削除メソッド
    public String ItemDelete(int id) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "DELETE FROM item WHERE id = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            //PreparedStatementを実行
            int records = pstmt.executeUpdate();

            //成功した場合
            if(records != 0) {

            	return "成功";

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
    public String ItemUpdate(int id , String name , String detail , int price , String imagefile) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "UPDATE item SET name = ? , detail = ? , price = ? , file = ? WHERE id = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, detail);
            pstmt.setInt(3, price);
            pstmt.setString(4, imagefile);
            pstmt.setInt(5, id);

            //PreparedStatementを実行
            int records = pstmt.executeUpdate();

            //成功した場合
            if(records != 0) {

            	return "成功";

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
