package controller;

public class Client {
    Server server = new Server();

    public void connectToServer(String name) {
        server.addPlayer(name);
    }
}
