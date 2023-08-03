package org.example.server;

import org.example.dto.RequestDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {
    private static DatagramSocket myServer;
    private static int serverPort = 9901;
    private static DatagramPacket receivePacket = null;
    public static void main(String[] args) {
        openServer(Main.serverPort);
        System.out.println("UDP server is running...");
        while(true){
            RequestDTO request = receiveData();
            ServerController controller = new ServerController(request,myServer,receivePacket);
            controller.start();
        }
    }
    private static void openServer(int portNumber){
        try {
            myServer = new DatagramSocket(portNumber);
        }catch(IOException e) {
            System.out.println(e);
        }
    }
    public static RequestDTO receiveData(){
        try {
            byte[] receiveData = new byte[2048];

            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            myServer.receive(receivePacket);
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);

            ObjectInputStream ois = new ObjectInputStream(bais);
            RequestDTO request= (RequestDTO) ois.readObject();
            return request;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}