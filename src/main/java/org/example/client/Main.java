package org.example.client;

import org.example.client.controller.ProducerController;
import org.example.client.ui.ProducerUI;

public class Main {
    public static void main(String[] args) {
//        WineUI view = new WineUI();
//        WineController control = new WineController(view);
//        view.setVisible(true);

        ProducerUI producerView = new ProducerUI();
        ProducerController control = new ProducerController(producerView);
        producerView.setVisible(true);
    }
}