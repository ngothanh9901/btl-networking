package org.example.server.service;

import org.example.dto.ResponseDTO;
import org.example.model.Wine;
import org.example.server.dao.WineDAO;

import java.util.List;

public class WineService {
  public static ResponseDTO addWine(Wine wine){
    int result = WineDAO.addWine(wine);
    if(result==0) return new ResponseDTO("error",null);
    return new ResponseDTO("ok",null);
  }
  public static ResponseDTO getAll(){
    List<Wine> result = WineDAO.getAll();
    return new ResponseDTO("ok",result);
  }
  public static ResponseDTO updateWine(Wine wine){
    int result = WineDAO.updateWine(wine);
    if(result==0) return new ResponseDTO("error",null);
    return new ResponseDTO("ok",null);
  }
  public static ResponseDTO deleteWineByCode(String code){
    int result = WineDAO.deleteWineByCode(code);
    if(result==0) return new ResponseDTO("error",null);
    return new ResponseDTO("ok",null);
  }
}
