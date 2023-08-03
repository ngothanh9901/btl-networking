package org.example.client.controller;

import org.example.client.ui.ProducerUI;
import org.example.client.ui.WineUI;
import org.example.dto.RequestDTO;
import org.example.dto.ResponseDTO;
import org.example.model.Producer;
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
import java.util.stream.Collectors;

public class WineController extends Controller implements ActionListener{
    private WineUI view;
    private int serverPort = 9901;
    private int clientPort = 6666;
    private String serverHost = "localhost";
    private List<Producer> producers = null;

    public WineController(WineUI view){
        super(view);
        this.view = view;
        // Add action listeners
        view.btnSearch.addActionListener(this);
        view.btnAdd.addActionListener(this);
        view.btnUpdate.addActionListener(this);
        view.btnDelete.addActionListener(this);
        view.btnClear.addActionListener(this);
        view.btnMenu.addActionListener(this);



        RequestDTO request = new RequestDTO("getAllProducer",null);
        sendData(request);
        ResponseDTO response = receiveData();
        producers = (List<Producer>) response.getValue();

        view.cboManufacturer.addItem(new Producer(null,"Không lựa chọn",null));
        for(Producer producer:producers){
            view.cboManufacturer.addItem(producer);
        }

        view.tblWine.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int rowIndex = view.tblWine.getSelectedRow();
                if(rowIndex>=0){
                    view.txtCode.setText((String) view.tblModel.getValueAt(rowIndex, 0));
                    view.txtName.setText((String) view.tblModel.getValueAt(rowIndex, 1));
                    view.txtAlcoholContent.setText(String.valueOf(view.tblModel.getValueAt(rowIndex, 2)));
                    view.txtYear.setText(String.valueOf( view.tblModel.getValueAt(rowIndex, 3)));
                    view.txtImage.setText(String.valueOf(view.tblModel.getValueAt(rowIndex,4)));

                    Producer producer = producers.stream().filter(i->i.getName().equals(view.tblModel.getValueAt(rowIndex,5))).collect(Collectors.toList()).get(0);
                    view.cboManufacturer.setSelectedItem(producer);
                }
            }
        });

        showWines(null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.btnSearch) {

            Wine wine = convertToModel();
            RequestDTO request = new RequestDTO("searchWine", wine);
            showWines(request);

            // TODO: Search for wine based on criteria and update table
        } else if (e.getSource() == view.btnAdd) {
            Wine wine = convertToModel();
            Producer producer = (Producer) view.cboManufacturer.getSelectedItem();
            Object[] row = new Object[]{wine.getCode(),wine.getName(), wine.getConcentration(),wine.getYearManufacture(),wine.getImage(), producer.getName()};

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
                Wine wine = convertToModel();
                Producer producer = (Producer) view.cboManufacturer.getSelectedItem();

                RequestDTO request = new RequestDTO("updateWine",wine);
                sendData(request);

                ResponseDTO response = receiveData();

                if(response.getStatus().equals("ok")){
                    view.tblModel.setValueAt(wine.getCode(), rowIndex, 0);
                    view.tblModel.setValueAt(wine.getName(), rowIndex, 1);
                    view.tblModel.setValueAt(wine.getConcentration(), rowIndex, 2);
                    view.tblModel.setValueAt(wine.getYearManufacture(), rowIndex, 3);
                    view.tblModel.setValueAt(wine.getImage(),rowIndex,4);
                    view.tblModel.setValueAt(producer.getName(), rowIndex, 5);
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
        }else if(e.getSource() == view.btnClear){
            view.txtCode.setText("");
            view.txtName.setText("");
            view.txtAlcoholContent.setText("");
            view.txtYear.setText("");
            view.txtImage.setText("");
            view.cboManufacturer.setSelectedIndex(0);
        }else if(e.getSource() == view.btnMenu){
            this.closeConnection();
            ProducerUI producerView = new ProducerUI();
            Controller control = new ProducerController(producerView);
            producerView.setVisible(true);

            view.setVisible(false);
            view.dispose();
        }
    }
    private void showWines(RequestDTO request){

        while (view.tblModel.getRowCount() > 0) {
            view.tblModel.removeRow(0);
        }

        // get all Wines
        if(request==null) request = new RequestDTO("getAll",null);
        sendData(request);
        ResponseDTO response = receiveData();
        List<Wine> wines = (List<Wine>) response.getValue();

        //show all wines to UI
        for(Wine wine:wines){
            Producer producer = producers.stream().filter(i->i.getId().equals(wine.getProducerId())).collect(Collectors.toList()).get(0);
            Object[] row = new Object[]{wine.getCode(),wine.getName(), wine.getConcentration(),wine.getYearManufacture(),wine.getImage(),producer.getName()};
            view.tblModel.addRow(row);
        }

    }
    private Wine convertToModel(){
        String code = view.txtCode.getText();
        String name = view.txtName.getText();
        Double alcoholContent = null;
        try {
            if(view.txtAlcoholContent.getText()!=null && !view.txtAlcoholContent.getText().isBlank()) alcoholContent = Double.valueOf(view.txtAlcoholContent.getText());
        } catch (NumberFormatException ne){
            JOptionPane.showMessageDialog(view, "malformed Content field");
        }
        Long year = null;
        try {
            if(view.txtYear.getText() !=null && !view.txtYear.getText().isBlank()) year = Long.valueOf(view.txtYear.getText());
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(view, "malformed YEAR field");
        }
        String image = view.txtImage.getText();
        Producer manufacturer = (Producer) view.cboManufacturer.getSelectedItem();


        Wine wine = new Wine(code,name,alcoholContent,year,image, manufacturer.getId());
        return wine;
    }
}
