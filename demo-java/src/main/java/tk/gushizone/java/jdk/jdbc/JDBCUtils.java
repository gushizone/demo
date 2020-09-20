/**
 * Copyright (c) https://github.com/gushizone
 */
package tk.gushizone.java.jdk.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author gushizone@gmail.com
 * @createDate 2017/12/26 9:42
 * @desc
 */
public class JDBCUtils {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://127.0.0.1:3306/employees";
    private static String user = "root";
    private static String password = "root";

//    注册数据库的驱动
    static {
        try {
            Class.forName(driver);
//            DriverManager.registerDriver(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//        获取数据库连接
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //            释放数据库的资源(根据获得的顺序，反向关闭)
    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
//                加速GC
                resultSet = null;
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                statement = null;
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                connection = null;
            }
        }
    }

}
