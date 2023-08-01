package org.example.server.dao;

import org.example.model.Wine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WineDAO {
  private static Connection connection = new DBContext().connection;
  private static PreparedStatement ps = null;
  public static int addWine(Wine wine){
    String sql = "INSERT INTO wine(code,name,concentration,yearManufacture,image,producerId) VALUES (?,?,?,?,?,?)";
    int result=0;
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1,wine.getCode());
      ps.setString(2,wine.getName());
      ps.setDouble(3,wine.getConcentration());
      ps.setLong(4,wine.getYearManufacture());
      ps.setString(5,wine.getImage());
      ps.setLong(6,wine.getProducerId());

      result = ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return result;
  }
}
