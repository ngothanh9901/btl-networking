package org.example.client;

import org.example.client.controller.Controller;
import org.example.client.controller.ProducerController;
import org.example.client.controller.WineController;
import org.example.client.ui.ProducerUI;
import org.example.client.ui.WineUI;

public class Main {
    public static void main(String[] args) {
//        ProducerUI producerView = new ProducerUI();
//        Controller control = new ProducerController(producerView);
//        producerView.setVisible(true);

        WineUI wineView = new WineUI();
        Controller control = new WineController(wineView);
        wineView.setVisible(true);
    }
}