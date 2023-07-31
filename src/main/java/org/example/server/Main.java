package org.example.server;

import org.example.client.WineController;
import org.example.client.WineUI;

public class Main {
    public static void main(String[] args) {
        WineServerView view = new WineServerView();

        WineServer control = new WineServer(view);
    }
}