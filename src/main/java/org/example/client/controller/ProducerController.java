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
import java.util.List;

public class ProducerController extends Controller implements ActionListener {
    private ProducerUI view;
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
            Producer producer = convertToModel();
            Object[] row = new Object[]{producer.getCode(),producer.getName(),producer.getDescription()};

            RequestDTO request = new RequestDTO("addProducer",producer);
            this.sendRequest(request,row);

            view.txtCode.setText("");
            view.txtName.setText("");
            view.txtDescription.setText("");
        } else if (e.getSource() == view.btnUpdate) {
            int rowIndex = view.tblProducer.getSelectedRow();
            if (rowIndex >= 0) {
                Producer producer = convertToModel();

                RequestDTO request = new RequestDTO("updateProducer", producer);
                sendData(request);

                ResponseDTO response = receiveData();

                if (response.getStatus().equals("ok")) {
                    view.tblModel.setValueAt(producer.getCode(), rowIndex, 0);
                    view.tblModel.setValueAt(producer.getName(), rowIndex, 1);
                    view.tblModel.setValueAt(producer.getDescription(), rowIndex, 2);
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
    private Producer convertToModel(){
        String code = view.txtCode.getText();
        String name = view.txtName.getText();
        String description = view.txtDescription.getText();

        Producer producer = new Producer(code, name, description);
        return producer;
    }
}
