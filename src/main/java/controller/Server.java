package controller;

import model.Manager;

public class Server {
    Manager manager = new Manager();

    public void addPlayer(String name) {
        manager.addPlayer(name);
    }
}
