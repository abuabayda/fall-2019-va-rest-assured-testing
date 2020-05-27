package com.DBUtil;

import java.sql.*;
import java.util.*;

public class DBUtility {

  private static Connection connection;
  private static PreparedStatement statement;
  private static ResultSet resultSet;

  public static void establishConnection(DBType dbType) throws SQLException {

    // connecting to db based in db information
    try {
      switch (dbType) {
        case MYSQL:
          connection = DriverManager.getConnection(ConfigReader.getProperty("l.jdbc.url"),
               ConfigReader.getProperty("l.username"),
               ConfigReader.getProperty("l.password"));
          break;
        default:
          connection = null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // getting row count of certain sql query

  public static int getRowCount(String sql) {
    try {
      statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, resultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(sql);
      resultSet.last();
      return resultSet.getRow();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  //getting list of run sql query ,
  public static List<Map<String, Object>> runSqlQuery(String sql) {

    try {
      statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery();

      List<Map<String, Object>> list = new ArrayList<>();
      ResultSetMetaData rsMdata = resultSet.getMetaData();

      int colCount = rsMdata.getColumnCount();

      while (resultSet.next()) {
        Map<String, Object> rowMap = new LinkedHashMap<>();
        for (int col = 1; col <= colCount; col++) {
          rowMap.put(rsMdata.getColumnName(col), resultSet.getObject(col));
        }
        list.add(rowMap);
      }
      return list;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  public static void closeConnection() {

    try {
      if (resultSet != null && resultSet.isClosed()) {
        resultSet.close();
      }
      if (statement != null && statement.isClosed()) {
        statement.close();
      }
      if (connection != null && connection.isClosed()) {
        connection.close();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }


  }
}
