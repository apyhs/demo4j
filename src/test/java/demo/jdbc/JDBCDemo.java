package demo.jdbc;

import com.mysql.jdbc.Connection;
import org.junit.Test;

import java.sql.*;

public class JDBCDemo {
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://dbut";
    private String user = "root";
    private String password = "root";

    @Test
    public void test0() throws Exception {
        Class.forName(driver);
        Connection conn = (Connection) DriverManager.getConnection(url, user, password);
        ResultSet resultSet = conn.createStatement().executeQuery("select * from user where id = '1';");
        if (resultSet.next()) {
            System.out.print(String.format("%s - %s - %s - %s"
                    , resultSet.getString(1)
                    , resultSet.getString(2)
                    , resultSet.getString(3)
                    , resultSet.getString(4)));
        } else {
            System.out.println("Nothing!");
        }
        resultSet.close();
        conn.close();
    }

    @Test
    public void test1() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, user, password);
            PreparedStatement prepStat = conn.prepareStatement("select * from t_user where id = ?;");
            prepStat.setInt(1, 1);
            ResultSet resultSet = prepStat.executeQuery();
            if (resultSet.next()) {
                System.out.print(String.format("%s - %s - %s - %s"
                        , resultSet.getString(1)
                        , resultSet.getString(2)
                        , resultSet.getString(3)
                        , resultSet.getString(4)));
            } else {
                System.out.println("Nothing!");
            }
            resultSet.close();
            prepStat.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, user, password);
            PreparedStatement prepStat = conn.prepareStatement("insert into user value(null, ?, ?, ?, ?);");
            prepStat.setString(1, "test");
            prepStat.setDate(2, new Date(new java.util.Date().getTime()));
            prepStat.setString(3, "test");
            prepStat.setString(4, "test");
            System.out.println(prepStat.executeUpdate());
            prepStat.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
