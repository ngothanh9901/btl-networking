package org.example.server;

public class Main {
    public static void main(String[] args) {
        ServerView view = new ServerView();

        ServerController control = new ServerController(view);
    }
}