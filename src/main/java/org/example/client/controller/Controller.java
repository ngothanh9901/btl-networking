package org.example.client.controller;

import org.example.client.ui.ProducerUI;
import org.example.client.ui.View;
import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Controller {
  private View view;
  private int serverPort = 9901;
  private int clientPort = 6669;
  private String serverHost = "localhost";
  private DatagramSocket myClient;

  public Controller(View view) {
    this.view = view;
    openConnection();
  }

  private ResponseDTO receiveData(){
    ResponseDTO response = null;
    try {

      byte[] receiveData = new byte[1024];
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      myClient.receive(receivePacket);
      ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);

      ObjectInputStream ois = new ObjectInputStream(bais);
      response = (ResponseDTO) ois.readObject();

    } catch (Exception ex) {
      view.showMessage("Some error has occurred");
    }
    return response;
  }
  private void sendData(RequestDTO request){
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(request);
      oos.flush();
      InetAddress IPAddress = InetAddress.getByName(serverHost);
      byte[] sendData = baos.toByteArray();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);

      myClient.send(sendPacket);
    } catch (Exception ex) {
      view.showMessage(ex.getStackTrace().toString());
    }
  }
  private void openConnection(){
    try {
      myClient = new DatagramSocket(clientPort);
    } catch (Exception ex) {
      view.showMessage(ex.getStackTrace().toString());
    }
  }
}
