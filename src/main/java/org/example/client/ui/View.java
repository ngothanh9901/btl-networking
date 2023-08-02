package org.example.client.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class View extends JFrame {
  public DefaultTableModel tblModel;
  public void showMessage(String msg){
    JOptionPane.showMessageDialog(this, msg);
  }

  public View(String title) throws HeadlessException {
    super(title);
  }
}
