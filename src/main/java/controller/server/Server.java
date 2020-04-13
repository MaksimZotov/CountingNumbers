package controller.server;

import controller.obligations.Model;
import model.manager.Manager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private Model manager;
    public Model getManager() { return manager; }

    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public ArrayList<ClientHandler> getClientHandlers() { return clientHandlers; }

    public Server(Manager manager) {
        this.manager = manager;
    }

    private ServerSocket serverSocket;

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
}
