package org.example.client;

import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;
import org.example.model.Wine;

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

public class WineController implements ActionListener{
    private WineUI view;
    private int serverPort = 9901;
    private int clientPort = 6666;
    private String serverHost = "localhost";
    private DatagramSocket myClient;

    public WineController(WineUI view){
        this.view = view;
        // Add action listeners
        view.btnSearch.addActionListener(this);
        view.btnAdd.addActionListener(this);
        view.btnUpdate.addActionListener(this);
        view.btnDelete.addActionListener(this);

        openConnection();
        showWines();

        view.tblWine.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int rowIndex = view.tblWine.getSelectedRow();
                if(rowIndex>=0){
                    view.txtCode.setText((String) view.tblModel.getValueAt(rowIndex, 0));
                    view.txtName.setText((String) view.tblModel.getValueAt(rowIndex, 1));
                    view.txtAlcoholContent.setText(String.valueOf(view.tblModel.getValueAt(rowIndex, 2)));
                    view.txtYear.setText(String.valueOf( view.tblModel.getValueAt(rowIndex, 3)));
                    view.txtImage.setText(String.valueOf(view.tblModel.getValueAt(rowIndex,4)));
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.btnSearch) {
            String code = view.txtCode.getText();
            String name = view.txtName.getText();
            String image = view.txtImage.getText();
            String alcoholContent = view.txtAlcoholContent.getText();
            String year = view.txtYear.getText();
            String manufacturer = (String) view.cboManufacturer.getSelectedItem();
            // TODO: Search for wine based on criteria and update table
        } else if (e.getSource() == view.btnAdd) {
            String code = view.txtCode.getText();
            String name = view.txtName.getText();
            String alcoholContent = view.txtAlcoholContent.getText();
            String year = view.txtYear.getText();
            String image = view.txtImage.getText();
            String manufacturer = (String) view.cboManufacturer.getSelectedItem();


            Wine wine = new Wine(code,name,Double.valueOf(alcoholContent),Long.valueOf(year),image,Long.valueOf(manufacturer));
            Object[] row = new Object[]{wine.getCode(),wine.getName(), wine.getConcentration(),wine.getYearManufacture(),wine.getImage(), wine.getProducerId()};

            RequestDTO request = new RequestDTO("addWine",wine);
            sendData(request);

            ResponseDTO response = receiveData();

            if(response.getStatus().equals("ok")){
                view.tblModel.addRow(row);
            }else{
                JOptionPane.showMessageDialog(view, "An error has appeared");
            }

            view.txtCode.setText("");
            view.txtName.setText("");
            view.txtAlcoholContent.setText("");
            view.txtYear.setText("");
            view.cboManufacturer.setSelectedIndex(0);
        } else if (e.getSource() == view.btnUpdate) {
            int rowIndex = view.tblWine.getSelectedRow();
            if (rowIndex >= 0) {
                String code = view.txtCode.getText();
                String name = view.txtName.getText();
                String alcoholContent = view.txtAlcoholContent.getText();
                String year = view.txtYear.getText();
                String image = view.txtImage.getText();
                String manufacturer = (String) view.cboManufacturer.getSelectedItem();

                Wine wine = new Wine(code,name,Double.valueOf(alcoholContent),Long.valueOf(year),image,Long.valueOf(manufacturer));
                Object[] row = new Object[]{wine.getCode(),wine.getName(), wine.getConcentration(),wine.getYearManufacture(),wine.getImage(), wine.getProducerId()};

                RequestDTO request = new RequestDTO("updateWine",wine);
                sendData(request);

                ResponseDTO response = receiveData();

                if(response.getStatus().equals("ok")){
                    view.tblModel.setValueAt(code, rowIndex, 0);
                    view.tblModel.setValueAt(name, rowIndex, 1);
                    view.tblModel.setValueAt(alcoholContent, rowIndex, 2);
                    view.tblModel.setValueAt(year, rowIndex, 3);
                    view.tblModel.setValueAt(image,rowIndex,4);
                    view.tblModel.setValueAt(manufacturer, rowIndex, 5);
                }else{
                    JOptionPane.showMessageDialog(view, "Some errors have occurred");
                }

            } else {
                JOptionPane.showMessageDialog(view, "Please select a wine to update.");
            }
        } else if (e.getSource() == view.btnDelete) {
            int rowIndex = view.tblWine.getSelectedRow();
            if (rowIndex >= 0) {
                String code = (String) view.tblModel.getValueAt(rowIndex,0);
                RequestDTO request = new RequestDTO("deleteWineByCode",code);
                sendData(request);

                ResponseDTO response = receiveData();
                if(response.getStatus().equals("ok")) view.tblModel.removeRow(rowIndex);

                view.txtCode.setText("");
                view.txtName.setText("");
                view.txtAlcoholContent.setText("");
                view.txtYear.setText("");
                view.cboManufacturer.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(view, "Please select a wine to delete.");
            }
        }
    }

    private void openConnection(){
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
    private void showWines(){
        RequestDTO request = new RequestDTO("getAll",null);
        sendData(request);

        ResponseDTO response = receiveData();
        List<Wine> wines = (List<Wine>) response.getValue();

        for(Wine wine:wines){
            Object[] row = new Object[]{wine.getCode(),wine.getName(), wine.getConcentration(),wine.getYearManufacture(),wine.getImage(), wine.getProducerId()};
            view.tblModel.addRow(row);
        }
    }
}
