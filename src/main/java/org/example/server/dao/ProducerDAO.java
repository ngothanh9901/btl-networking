package org.example.server.dao;

import org.example.model.Producer;
import org.example.model.Wine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducerDAO {
  private static Connection connection = new DBContext().connection;
  private static PreparedStatement ps = null;
  private static ResultSet rs = null;

  public static List<Producer> getAll(){
    List<Producer> producers = new ArrayList<>();
    String sql = "SELECT * FROM producer";
    try {
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
        Producer producer = new Producer(rs.getLong("id"),rs.getString("code"),rs.getString("name"),rs.getString("description"));
        producers.add(producer);
      }
      return producers;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public static int addProducer(Producer producer){
    String sql = "INSERT INTO producer(code,name,description) VALUES (?,?,?)";
    int result=0;
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1,producer.getCode());
      ps.setString(2,producer.getName());
      ps.setString(3,producer.getDescription());

      result = ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return result;
  }
  public static int updateProducer(Producer producer){
    String sql ="UPDATE producer SET code=?,name=?,description=? WHERE code=?";
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1,producer.getCode());
      ps.setString(2,producer.getName());
      ps.setString(3,producer.getDescription());
      ps.setString(4,producer.getCode());

      int result = ps.executeUpdate();
      return result;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public static int deleteProducerByCode(String code){
    String sql ="DELETE FROM producer WHERE code = ?";
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1,code);
      int result = ps.executeUpdate();
      return result;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public static List<Producer> searchProducer(Producer parameter){
    List<Producer> result = new ArrayList<>();

    String sql = "SELECT * FROM producer WHERE 1=1";
    if(!parameter.getCode().isBlank()) sql += " AND code LIKE '%" + parameter.getCode() + "%'";
    if(!parameter.getName().isBlank()) sql += " AND name LIKE '%" + parameter.getName() + "%'";
    if(!parameter.getDescription().isBlank()) sql += " AND description LIKE '%" + parameter.getDescription() + "%'";

    try {
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
        Producer producer = new Producer(rs.getString("code"),rs.getString("name"),rs.getString("description"));
        result.add(producer);
      }
      return result;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
