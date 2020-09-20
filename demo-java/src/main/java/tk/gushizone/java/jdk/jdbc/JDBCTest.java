/**
 * Copyright (c) https://github.com/gushizone
 */
package tk.gushizone.java.jdk.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @createDate 2017/12/18 14:39
 * @desc JDBC测试类
 */
@Slf4j
@RunWith(JUnit4.class)
public class JDBCTest {

    private Connection connection = null;

    private PreparedStatement statement = null;

    private ResultSet resultSet = null;

    /**
     * JDBC传统方式
     */
    @Test
    public void baseTest() {

        List<Employees> employees = null;

        try {
//            获取数据库连接
            connection = JDBCUtils.getConnection();
//            查询（JDBC模板式代码）
            employees = queryList(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            释放资源
            JDBCUtils.release(connection, statement, resultSet);
        }
//            打印结果集
        log.info("employees: {}", employees);

    }

    private List<Employees> queryList(Connection connection) throws SQLException {

        String sql = "select * from employees limit 1";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();

        List<Employees> Employees = new ArrayList<>();
        while (resultSet.next()) {

            Employees employee = new Employees();

            employee.setEmpNo(resultSet.getInt("emp_no"));
            employee.setBirthDate(resultSet.getDate("birth_date"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));

            Employees.add(employee);
        }
        return Employees;
    }


    /**
     * 查询消息列表
     */
//    private List<Message> getMessages(Connection connection) throws SQLException {
//        String sql = "select ID,COMMAND,DESCRIPTION,CONTENT from MESSAGE";
//        statement = connection.prepareStatement(sql);
//        resultSet = statement.executeQuery();
//
//        List<Message> messageList = new ArrayList<>();
//        while (resultSet.next()) {
//            Message message = new Message();
//            messageList.add(message);
//            message.setId(resultSet.getString("ID"));
//            message.setCommand(resultSet.getString("COMMAND"));
//            message.setDescription(resultSet.getString("DESCRIPTION"));
//            message.setContent(resultSet.getString("CONTENT"));
//        }
//        return messageList;
//    }

}
