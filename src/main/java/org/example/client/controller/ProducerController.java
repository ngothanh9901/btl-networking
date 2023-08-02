package org.example.client.controller;

import org.example.client.ui.ProducerUI;
import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;
import org.example.model.Producer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class ProducerController extends Controller implements ActionListener {
    private ProducerUI view;
    private int serverPort = 9901;
    private int clientPort = 6669;
    private String serverHost = "localhost";
    private DatagramSocket myClient;

    public ProducerController(ProducerUI view){
        super(view);
        this.view = view;
        // Add action listeners
        view.btnSearch.addActionListener(this);
        view.btnAdd.addActionListener(this);
        view.btnUpdate.addActionListener(this);
        view.btnDelete.addActionListener(this);
        view.btnClear.addActionListener(this);

        openConnection();
        showProducer();

        view.tblProducer.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int rowIndex = view.tblProducer.getSelectedRow();
                if(rowIndex>=0){
                    view.txtCode.setText((String) view.tblModel.getValueAt(rowIndex,0));
                    view.txtName.setText((String) view.tblModel.getValueAt(rowIndex, 1));
                    view.txtDescription.setText(String.valueOf(view.tblModel.getValueAt(rowIndex, 2)));
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btnAdd) {
            String code = view.txtCode.getText();
            String name = view.txtName.getText();
            String description = view.txtDescription.getText();


            Producer producer = new Producer(code,name,description);
            Object[] row = new Object[]{producer.getCode(),producer.getName(),producer.getDescription()};

            RequestDTO request = new RequestDTO("addProducer",producer);
            sendData(request);

            ResponseDTO response = receiveData();

            if(response.getStatus().equals("ok")){
                view.tblModel.addRow(row);
            }else{
                JOptionPane.showMessageDialog(view, "An error has appeared");
            }

            view.txtCode.setText("");
            view.txtName.setText("");
            view.txtDescription.setText("");
        } else if (e.getSource() == view.btnUpdate) {
            int rowIndex = view.tblProducer.getSelectedRow();
            if (rowIndex >= 0) {
                String code = view.txtCode.getText();
                String name = view.txtName.getText();
                String description = view.txtDescription.getText();

                Producer producer = new Producer(code, name, description);
                Object[] row = new Object[]{producer.getCode(), producer.getName(),producer.getDescription()};

                RequestDTO request = new RequestDTO("updateProducer", producer);
                sendData(request);

                ResponseDTO response = receiveData();

                if (response.getStatus().equals("ok")) {
                    view.tblModel.setValueAt(code, rowIndex, 0);
                    view.tblModel.setValueAt(name, rowIndex, 1);
                    view.tblModel.setValueAt(description, rowIndex, 2);
                } else {
                    JOptionPane.showMessageDialog(view, "Some errors have occurred");
                }

            } else {
                JOptionPane.showMessageDialog(view, "Please select a wine to update.");
            }
        } else if (e.getSource() == view.btnDelete) {
            int rowIndex = view.tblProducer.getSelectedRow();
            if (rowIndex >= 0) {
                String code = (String) view.tblModel.getValueAt(rowIndex,0);
                RequestDTO request = new RequestDTO("deleteProducerByCode",code);
                sendData(request);

                ResponseDTO response = receiveData();
                if(response.getStatus().equals("ok")) view.tblModel.removeRow(rowIndex);

                view.txtCode.setText("");
                view.txtName.setText("");
                view.txtDescription.setText("");
            } else {
                JOptionPane.showMessageDialog(view, "Please select a wine to delete.");
            }
        } else if(e.getSource() == view.btnClear){
            view.txtCode.setText("");
            view.txtName.setText("");
            view.txtDescription.setText("");
        }
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
            view.showMessage(ex.getStackTrace().toString());
        }
        return response;
    }
    private void openConnection(){
        try {
            myClient = new DatagramSocket(clientPort);
        } catch (Exception ex) {
            view.showMessage(ex.getStackTrace().toString());
        }
    }
    private void showProducer(){
        RequestDTO request = new RequestDTO("getAllProducer",null);
        sendData(request);

        ResponseDTO response = receiveData();
        List<Producer> producers = (List<Producer>) response.getValue();

        for(Producer producer: producers){
            Object[] row = new Object[]{producer.getCode(),producer.getName(),producer.getDescription()};
            view.tblModel.addRow(row);
        }
    }
}
