package org.example.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
  public Connection connection;
  public DBContext() {
    String dbUrl = "jdbc:mysql://localhost:3306/ltm";
    String dbClass = "com.mysql.jdbc.Driver";
    try {
      Class.forName(dbClass);
      connection = DriverManager.getConnection (dbUrl,"root", "123456789");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
