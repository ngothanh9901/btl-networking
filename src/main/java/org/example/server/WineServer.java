package org.example.server;

import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;
import org.example.model.Wine;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WineServer {
    private WineServerView view;
    private Connection con;
    private DatagramSocket myServer;
    private int serverPort = 9901;
    private DatagramPacket receivePacket = null;

    public WineServer(WineServerView view){
        this.view = view;
        getDBConnection("ltm", "root", "123456789");
        openServer(serverPort);
        view.showMessage("UDP server is running...");
        while(true){
            listening();
        }
    }
    private void getDBConnection(String dbName, String username, String
            password){

        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection (dbUrl, username, password);
        }catch(Exception e) {
            view.showMessage(e.getStackTrace().toString());
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
        if(request.getLabel().equals("addWine")){
            Wine wine = (Wine) request.getValue();
            boolean result = addWine(wine);
            if(result){
                response = new ResponseDTO("ok",null);
            }
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


    private boolean addWine(Wine wine){
        String sql = "INSERT INTO wine(code,name,concentration,yearManufacture,image,producerId) VALUES (?,?,?,?,?,?)";
        boolean result=false;
        try {
            PreparedStatement myStmt = con.prepareStatement(sql);
            myStmt.setString(1,wine.getCode());
            myStmt.setString(2,wine.getName());
            myStmt.setDouble(3,wine.getConcentration());
            myStmt.setLong(4,wine.getYearManufacture());
            myStmt.setString(5,wine.getImage());
            myStmt.setLong(6,wine.getProducerId());

            result = myStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
