package com.cbt.tests.day12_Could_be_last_day;

import com.DBUtil.DBUtility;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class JDBCExReview {
    String url = "jdbc:oracle:thin:@34.203.75.130:1521:xe";
    String username = "hr";
    String password = "hr";

    @Test
    public void setup() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(url, username, password);
        String sql = "SELECT * FROM regions";
        //execute a query
        Statement statement = connection.createStatement();
        // store the result
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println(resultSet.getMetaData().getColumnCount());
        System.out.println("=====================Regions Names========================");

        while (resultSet.next()) {
            String name = resultSet.getString("region_name");
            System.out.println("name = " + name);
        }
        System.out.println("=====================Countries Names========================");
        sql = "select * from countries";
        resultSet = statement.executeQuery(sql);

        //go thru result and print country name
        while (resultSet.next()){
            String name =  resultSet.getString(2);
            System.out.println("name = " + name);
        }
    }
}