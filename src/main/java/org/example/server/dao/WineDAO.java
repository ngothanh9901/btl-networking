package org.example.server.dao;

import org.example.model.Wine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WineDAO {
  private static Connection connection = new DBContext().connection;
  private static PreparedStatement ps = null;
  private static ResultSet rs = null;
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
  public static int updateWine(Wine wine){
    String sql ="UPDATE wine SET code=?,name=?,concentration=?,yearManufacture=?,image=?,producerId=? WHERE code=?";
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1,wine.getCode());
      ps.setString(2,wine.getName());
      ps.setDouble(3,wine.getConcentration());
      ps.setLong(4,wine.getYearManufacture());
      ps.setString(5,wine.getImage());
      ps.setLong(6,wine.getProducerId());
      ps.setString(7, wine.getCode());

      int result = ps.executeUpdate();
      return result;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public static List<Wine> getAll(){
    List<Wine> wines = new ArrayList<>();
    String sql = "SELECT * FROM wine";
    try {
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
        Wine wine = new Wine(rs.getString("code"),rs.getString("name"),rs.getDouble("concentration"),rs.getLong("yearManufacture"),rs.getString("image"),rs.getLong("producerId"));
        wines.add(wine);
      }
      return wines;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public static int deleteWineByCode(String code){
    String sql ="DELETE FROM wine WHERE code = ?";
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1,code);
      int result = ps.executeUpdate();
      return result;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
