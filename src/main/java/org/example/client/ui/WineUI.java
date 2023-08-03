package org.example.client.ui;
import org.example.model.Producer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WineUI extends View{
    public JTextField txtCode, txtName, txtImage, txtAlcoholContent, txtYear;
    public JComboBox<Producer> cboManufacturer;
    public JButton btnSearch, btnAdd, btnUpdate, btnDelete,btnClear,btnMenu;
    public DefaultTableModel tblModel;
    public JTable tblWine;

    public WineUI() {
        super("Wine Management");

        // Create components
        JLabel lblCode = new JLabel("Code:");
        txtCode = new JTextField(10);
        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField(20);
        JLabel lblAlcoholContent = new JLabel("Wine Content:");
        txtAlcoholContent = new JTextField(10);
        JLabel lblYear = new JLabel("Year:");
        txtYear = new JTextField(10);
        JLabel lblImage = new JLabel("Image:");
        txtImage = new JTextField(20);

        JLabel lblManufacturer = new JLabel("Manufacturer:");
        cboManufacturer = new JComboBox<>();

        btnSearch = new JButton("Search");
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnMenu = new JButton("Menu");

        tblModel = new DefaultTableModel(new Object[]{"Code", "Name", "Wine Content", "Year", "Image","Manufacturer"}, 0);
        tblWine = new JTable(tblModel);

        // Create layout
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Search"));
        pnlSearch.add(lblCode);
        pnlSearch.add(txtCode);
        pnlSearch.add(lblName);
        pnlSearch.add(txtName);
        pnlSearch.add(lblAlcoholContent);
        pnlSearch.add(txtAlcoholContent);
        pnlSearch.add(lblYear);
        pnlSearch.add(txtYear);
        pnlSearch.add(lblImage);
        pnlSearch.add(txtImage);
        pnlSearch.add(lblManufacturer);
        pnlSearch.add(cboManufacturer);
        pnlSearch.add(btnSearch);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnSearch);
        pnlButtons.add(btnClear);
        pnlButtons.add(btnMenu);

        JScrollPane scrollPane = new JScrollPane(tblWine);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlSearch, BorderLayout.NORTH);
        pnlMain.add(scrollPane, BorderLayout.CENTER);
        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        // Add components to frame
        this.setContentPane(pnlMain);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}