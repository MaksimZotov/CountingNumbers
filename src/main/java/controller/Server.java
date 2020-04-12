package controller;

import controller.obligations.ManagerObligations;
import controller.obligations.ServerObligations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerObligations {
    ManagerObligations manager;

    public Server(ManagerObligations manager) { this.manager = manager; }

    private Socket clientSocket;
    private ServerSocket server;
    private BufferedReader in;
    private BufferedWriter out;

    @Override
    public void main() {
        try {
            try  {
                server = new ServerSocket(6666);
                System.out.println("The server was launched");
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String messageFromClient = in.readLine();

                    //Test
                    joinPlayer(messageFromClient);
                    answerOnJoinPlayer(messageFromClient);
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

    @Override public void joinPlayer(String name) { manager.joinPlayer(name); }
    @Override public void movePlayer(String name, String direction) { manager.movePlayer(name, direction); }
    @Override public void increaseCounterOfPlayer(String name) { manager.increaseCounterOfPlayer(name); }

    @Override
    public void answerOnJoinPlayer(String name) {
        System.out.println("Message From Client: " + name);
        try {
            out.write(manager.answerOnJoinPlayer() + '\n');
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void answerOnMovePlayer(String name) { }

    @Override
    public void answerOnIncreaseCounterOfPlayer(String name) { }

    @Override
    public void sendEventPlayerLost(String name) { }
}
