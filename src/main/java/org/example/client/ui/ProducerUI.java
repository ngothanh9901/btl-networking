package org.example.client.ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProducerUI extends View{
    public JTextField txtName, txtDescription,txtCode;
    public JButton btnSearch, btnAdd, btnUpdate, btnDelete,btnClear;
    public DefaultTableModel tblModel;
    public JTable tblProducer;
    public ProducerUI() {
        super("Wine Management");

        // Create components
        JLabel lblCode = new JLabel("Code:");
        txtCode = new JTextField(20);
        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField(20);
        JLabel lblDescription = new JLabel("Description:");
        txtDescription = new JTextField(20);


        btnSearch = new JButton("Search");
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        tblModel = new DefaultTableModel(new Object[]{"Code","Name","Description"}, 0);
        tblProducer = new JTable(tblModel);

        // Create layout
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Search"));
        pnlSearch.add(lblCode);
        pnlSearch.add(txtCode);
        pnlSearch.add(lblName);
        pnlSearch.add(txtName);
        pnlSearch.add(lblDescription);
        pnlSearch.add(txtDescription);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnClear);

        JScrollPane scrollPane = new JScrollPane(tblProducer);

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
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
}