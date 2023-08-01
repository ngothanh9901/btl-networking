package org.example.server.service;

import org.example.dto.ResponseDTO;
import org.example.model.Wine;
import org.example.server.dao.WineDAO;

public class WineService {
  public static ResponseDTO addWine(Wine wine){
    int result = WineDAO.addWine(wine);
    if(result==0) return new ResponseDTO("error",null);
    return new ResponseDTO("ok",null);
  }
}
