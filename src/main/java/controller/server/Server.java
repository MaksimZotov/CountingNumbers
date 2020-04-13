package controller.server;

import controller.obligations.Model;
import model.manager.Manager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private Model manager;
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public Server(Manager manager) {
        this.manager = manager;
    }

    public void main() {
        try {
            try {
                serverSocket = new ServerSocket(6666);
                System.out.println("The server was launched");
                while (true) {
                    Socket socket = serverSocket.accept();
                    clientHandlers.add(new ClientHandler(socket, this));
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ClientHandler> getClientHandlers() { return clientHandlers; }

    Model getManager() { return manager; }
}
