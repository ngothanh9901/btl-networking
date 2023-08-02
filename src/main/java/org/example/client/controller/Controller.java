package org.example.client.controller;

import org.example.client.ui.ProducerUI;
import org.example.client.ui.View;
import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Controller {
  protected View view;
  protected int serverPort = 9901;
  protected int clientPort = 6669;
  protected String serverHost = "localhost";
  protected DatagramSocket myClient;

  public Controller(View view) {
    this.view = view;
    openConnection();
  }

  protected ResponseDTO receiveData(){
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
  protected void sendData(RequestDTO request){
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
  protected void openConnection(){
    try {
      myClient = new DatagramSocket(clientPort);
    } catch (Exception ex) {
      view.showMessage(ex.getStackTrace().toString());
    }
  }
  private void closeConnection(){
    try {

      myClient.close();
    } catch (Exception ex) {
      view.showMessage(ex.getStackTrace().toString());
    }
  }
  protected ResponseDTO sendRequest(RequestDTO request,Object[] row){
    sendData(request);
    ResponseDTO response = receiveData();

    if(response.getStatus().equals("ok")){
      view.tblModel.addRow(row);
    }else{
      JOptionPane.showMessageDialog(view, "An error has appeared");
    }
    return response;
  }
}
