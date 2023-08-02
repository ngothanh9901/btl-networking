package org.example.server;

import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;
import org.example.model.Wine;
import org.example.server.service.WineService;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerController {
    private ServerView view;
    private DatagramSocket myServer;
    private int serverPort = 9901;
    private DatagramPacket receivePacket = null;

    public ServerController(ServerView view){
        this.view = view;
        openServer(serverPort);
        view.showMessage("UDP server is running...");
        while(true){
            listening();
        }
    }
    private void openServer(int portNumber){
        try {
            myServer = new DatagramSocket(portNumber);
        }catch(IOException e) {
            view.showMessage(e.toString());
        }
    }
    private void listening(){
        ResponseDTO response = new ResponseDTO("error",null);
        RequestDTO request = receiveData();
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
            view.showMessage(ex.getStackTrace().toString());
        }
    }
    private RequestDTO receiveData(){
        try {

            byte[] receiveData = new byte[1024];

            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            myServer.receive(receivePacket);
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);

            ObjectInputStream ois = new ObjectInputStream(bais);
            RequestDTO request= (RequestDTO) ois.readObject();
            return request;
        } catch (Exception ex) {
            view.showMessage(ex.getStackTrace().toString());
        }
        return null;
    }
}
