package controller.server;

import controller.obligations.SessionManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private SessionManager sessionManager;
    private ServerSocket serverSocket;

    public Server(SessionManager sessionManager) { this.sessionManager = sessionManager; }

    public void main() {
        try {
            try {
                serverSocket = new ServerSocket(6666);
                while (true) {
                    Socket socket = serverSocket.accept();
                    sessionManager.addSocket(socket);
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
