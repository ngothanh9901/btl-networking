package org.example.server.service;

import org.example.dto.ResponseDTO;
import org.example.model.Producer;
import org.example.model.Wine;
import org.example.server.dao.ProducerDAO;
import org.example.server.dao.WineDAO;

import java.util.List;

public class ProducerService {
  public static ResponseDTO getAll(){
    List<Producer> result = ProducerDAO.getAll();
    return new ResponseDTO("ok",result);
  }
  public static ResponseDTO addProducer(Producer producer){
    int result = ProducerDAO.addProducer(producer);
    if(result==0) return new ResponseDTO("error",null);
    return new ResponseDTO("ok",null);
  }
  public static ResponseDTO updateProducer(Producer producer){
    int result = ProducerDAO.updateProducer(producer);
    if(result==0) return new ResponseDTO("error",null);
    return new ResponseDTO("ok",null);
  }
  public static ResponseDTO deleteProducerByCode(String code){
    int result = ProducerDAO.deleteProducerByCode(code);
    if(result==0) return new ResponseDTO("error",null);
    return new ResponseDTO("ok",null);
  }
}
