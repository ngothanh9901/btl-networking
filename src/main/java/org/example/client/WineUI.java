package org.example.client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WineUI extends JFrame{
    public JTextField txtCode, txtName, txtImage, txtAlcoholContent, txtYear;
    public JComboBox<String> cboManufacturer;
    public JButton btnSearch, btnAdd, btnUpdate, btnDelete;
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
        cboManufacturer = new JComboBox<String>(new String[]{"1", "2", "3", "4"});
        btnSearch = new JButton("Search");
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        tblModel = new DefaultTableModel(new Object[]{"Code", "Name", "Wine Content", "Year", "Manufacturer"}, 0);
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

    public void actionPerformed(ActionEvent e) {

    }
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        WineUI ui = new WineUI();
        ui.setVisible(true);
    }

    public JTextField getTxtCode() {
        return txtCode;
    }

    public void setTxtCode(JTextField txtCode) {
        this.txtCode = txtCode;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JTextField getTxtAlcoholContent() {
        return txtAlcoholContent;
    }

    public void setTxtAlcoholContent(JTextField txtAlcoholContent) {
        this.txtAlcoholContent = txtAlcoholContent;
    }

    public JTextField getTxtYear() {
        return txtYear;
    }

    public void setTxtYear(JTextField txtYear) {
        this.txtYear = txtYear;
    }

    public JComboBox<String> getCboManufacturer() {
        return cboManufacturer;
    }

    public void setCboManufacturer(JComboBox<String> cboManufacturer) {
        this.cboManufacturer = cboManufacturer;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public DefaultTableModel getTblModel() {
        return tblModel;
    }

    public void setTblModel(DefaultTableModel tblModel) {
        this.tblModel = tblModel;
    }

    public JTable getTblWine() {
        return tblWine;
    }

    public void setTblWine(JTable tblWine) {
        this.tblWine = tblWine;
    }
}