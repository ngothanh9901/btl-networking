package org.example.server;

import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;
import org.example.model.Producer;
import org.example.model.Wine;
import org.example.server.dao.ProducerDAO;
import org.example.server.dao.WineDAO;
import org.example.server.service.ProducerService;
import org.example.server.service.WineService;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerController extends Thread{
    private DatagramSocket myServer;
    private DatagramPacket receivePacket = null;
    private RequestDTO request;

    public ServerController(RequestDTO request,DatagramSocket myServer,DatagramPacket receivePacket){
        this.request = request;
        this.myServer = myServer;
        this.receivePacket = receivePacket;
    }
    public void run(){
        listening(request);
    }
    private void listening(RequestDTO request){
        ResponseDTO response = new ResponseDTO("error",null);
//        RequestDTO request = receiveData();
        System.out.println("Have a request");
        if(request.getLabel().equals("addWine")){
            Wine wine = (Wine) request.getValue();
            response = WineService.addWine(wine);
        }
        if(request.getLabel().equals("getAll")){
            response = WineService.getAll();
        }
        if(request.getLabel().equals("updateWine")){
            Wine wine = (Wine) request.getValue();
            response = WineService.updateWine(wine);
        }
        if(request.getLabel().equals("deleteWineByCode")){
             String code = (String) request.getValue();
            response = WineService.deleteWineByCode(code);
        }
        if(request.getLabel().equals("searchWine")){
            Wine wine = (Wine) request.getValue();
            response = WineService.searchWine(wine);
        }
        if(request.getLabel().equals("getAllProducer")){
            response = ProducerService.getAll();
        }
        if(request.getLabel().equals("addProducer")){
            Producer producer = (Producer) request.getValue();
            response = ProducerService.addProducer(producer);
        }
        if(request.getLabel().equals("updateProducer")){
            Producer producer = (Producer) request.getValue();
            response = ProducerService.updateProducer(producer);
        }
        if(request.getLabel().equals("deleteProducerByCode")){
            String code = (String) request.getValue();
            response = ProducerService.deleteProducerByCode(code);
        }
        if(request.getLabel().equals("searchProducer")){
            Producer producer = (Producer) request.getValue();
            response = ProducerService.searchProducer(producer);
        }
        sendData(response);
    }
    private void sendData(ResponseDTO response){
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();
            InetAddress IPAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            byte[] sendData = baos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, clientPort);

            myServer.send(sendPacket);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
