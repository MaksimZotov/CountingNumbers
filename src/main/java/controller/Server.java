package controller;

import model.Manager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Manager manager = new Manager();

    public void addPlayer(String name) {
        manager.addPlayer(name);
    }

    private static Socket clientSocket; 
    private static ServerSocket server; 
    private static BufferedReader in; 
    private static BufferedWriter out; 

    public static void interactionWithClient() {
        try {
            try  {
                server = new ServerSocket(4004);
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String messageFromClient = in.readLine();
                    out.write("You wrote : " + messageFromClient + "\n");
                    out.flush();
                } finally { 
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
