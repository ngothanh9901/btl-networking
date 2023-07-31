package org.example.client;

import org.example.client.WineController;
import org.example.client.WineUI;

public class Main {
    public static void main(String[] args) {
        WineUI view = new WineUI();
        WineController control = new WineController(view);
        view.setVisible(true);
    }
}