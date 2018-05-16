package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import base.DBManager;
import beans.UserBeans;


public class UserDao {

	//ユーザー登録メソッド
    public String UserTouroku(String loginId , String password1 , String name , String email , String birthdate) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "INSERT INTO user (login_id , password , name , email , birth_date , create_date) VALUES (? , ? , ? , ? , ? , ?)";

            //現在時刻の取得
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String todayC = format.format(today);

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginId);
            pstmt.setString(2, password1);
            pstmt.setString(3, name);
            pstmt.setString(4, email);
            pstmt.setString(5, birthdate);
            pstmt.setString(6, todayC);

            //PreparedStatementを実行
            int records = pstmt.executeUpdate();

            //成功した場合
            return "成功";

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

    //ユーザーログインメソッド
    public UserBeans UserLogin(String loginid , String password) {

       	Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "SELECT * FROM user WHERE login_id = ? AND password = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginid);
            pstmt.setString(2, password);

            //PreparedStatementを実行
            ResultSet rs = pstmt.executeQuery();

            //ログイン成功(レコードの内容を取得)
            if(rs.next()) {



            	int id = rs.getInt("id");
            	String loginId = rs.getString("login_id");
            	String dbPass = rs.getString("password");
            	String name = rs.getString("name");
            	String email = rs.getString("email");
            	String birthDate = rs.getString("birth_date");
            	String createDate = rs.getString("create_date");
            	String updateDate = rs.getString("update_date");

            	return new UserBeans(id , loginId , dbPass , name , email , birthDate , createDate , updateDate);

            }

            //ログイン失敗
            return null;

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
    public ArrayList<UserBeans> UserList() {

       	Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "SELECT * FROM user";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //PreparedStatementを実行
            ResultSet rs = pstmt.executeQuery();

            //ユーザーリストインスタンス生成
            ArrayList<UserBeans> userList = new ArrayList<UserBeans>();

            //レコードの内容を取得
            while(rs.next()) {

            	UserBeans user = new UserBeans();

            	user.setId(rs.getInt("id"));
            	user.setLoginId(rs.getString("login_id"));
            	user.setPassword(rs.getString("password"));
            	user.setName(rs.getString("name"));
            	user.setEmail(rs.getString("email"));
            	user.setBirthDate(rs.getString("birth_date"));
            	user.setCreateDate(rs.getString("create_date"));
            	user.setUpdateDate(rs.getString("update_date"));

            	userList.add(user);

            }

            //ユーザーリストをリターン
            return userList;

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

    //ユーザー検索メソッド
    public ArrayList<UserBeans> UserSearch(String loginid , String name , String email , String birthdate) {

       	Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //現在の日付(年)を取得
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String sToday = "'" + format.format(today) + "'";

            //SELECT文を準備
            String sql = "SELECT * FROM user WHERE birth_date <= " + sToday;

            //条件式の追加
            if(!(loginid.equals(""))) {
            	sql += "and login_id = '" + loginid + "'";
            }
            if(!(name.equals(""))) {
            	sql += "AND name =" + "'" + name + "'";
            }
            if(!(email.equals(""))) {
            	sql += "AND email =" + "'" + email + "'";
            }
            if(!(birthdate.equals(""))) {
            	sql += "AND birth_date =" + "'" + birthdate + "'";
            }

            // SELECTを実行し、結果表を取得
            Statement stmt = conn.createStatement();

            //Statementを実行
            ResultSet rs = stmt.executeQuery(sql);

            //ユーザーリストインスタンス生成
            ArrayList<UserBeans> userList = new ArrayList<UserBeans>();

            //レコードの内容を取得
            while(rs.next()) {

            	UserBeans user = new UserBeans();

            	user.setId(rs.getInt("id"));
            	user.setLoginId(rs.getString("login_id"));
            	user.setPassword(rs.getString("password"));
            	user.setName(rs.getString("name"));
            	user.setEmail(rs.getString("email"));
            	user.setBirthDate(rs.getString("birth_date"));
            	user.setCreateDate(rs.getString("create_date"));
            	user.setUpdateDate(rs.getString("update_date"));

            	userList.add(user);

            }

            //ユーザーリストをリターン
            return userList;

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

    //ユーザーID検索メソッド
    public UserBeans UserSearchId(int id) {

       	Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

          //SELECT文を準備
            String sql = "SELECT * FROM user WHERE id = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            //PreparedStatementを実行
            ResultSet rs = pstmt.executeQuery();

            //ログイン成功(レコードの内容を取得)
            if(rs.next()) {

            	int ID = rs.getInt("id");
            	String loginId = rs.getString("login_id");
            	String dbPass = rs.getString("password");
            	String name = rs.getString("name");
            	String email = rs.getString("email");
            	String birthDate = rs.getString("birth_date");
            	String createDate = rs.getString("create_date");
            	String updateDate = rs.getString("update_date");

            	return new UserBeans(ID , loginId , dbPass , name , email , birthDate , createDate , updateDate);

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

	//ユーザー削除メソッド
    public String UserDelete(int id) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "DELETE FROM user WHERE id = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            //PreparedStatementを実行
            int records = pstmt.executeUpdate();

            if(!(records == 0)){

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

  //ユーザー更新メソッド
    public String UserUpdate(int id , String password , String name , String birthdate ) {

        Connection conn = null;
        try {

            //データベースへ接続
            conn = DBManager.getConnection();

            //SELECT文を準備
            String sql = "UPDATE user SET password = ? , name = ? , birth_date = ? WHERE id = ?";

            //PreparedStatementを準備
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, name);
            pstmt.setString(3, birthdate);
            pstmt.setInt(4, id);

            //PreparedStatementを実行
            int records = pstmt.executeUpdate();

            if(!(records == 0)){

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
