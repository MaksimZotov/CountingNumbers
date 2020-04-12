package controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerObligations {
    RequestHandler handler;

    public Server(RequestHandler handler) { this.handler = handler; }

    public Server() { }

    private static Socket clientSocket; 
    private static ServerSocket server; 
    private static BufferedReader in; 
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try  {
                server = new ServerSocket(4004);
                System.out.println("The server was launched");
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String messageFromClient = in.readLine();
                    System.out.println("Message From Client: " + messageFromClient);
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

    @Override public void joinPlayer(String name) {
        handler.joinPlayer(name);
    }
    @Override public void movePlayer(String name, String direction) { handler.movePlayer(name, direction); }
    @Override public void increaseCounterOfPlayer(String name) { handler.increaseCounterOfPlayer(name); }

    @Override public void answerOnJoinPlayer(String name) { }
    @Override public void answerOnMovePlayer(String name) { }
    @Override public void answerOnIncreaseCounterOfPlayer(String name) { }
    @Override public void sendEventPlayerLost(String name) { }
}
