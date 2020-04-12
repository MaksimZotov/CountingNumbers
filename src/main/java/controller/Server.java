package controller;

import controller.obligations.ManagerObligations;
import controller.obligations.ServerObligations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements ServerObligations {
    private ManagerObligations manager;
    public ManagerObligations getManager() { return manager; }

    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public ArrayList<ClientHandler> getClientHandlers() { return clientHandlers; }

    public Server(ManagerObligations manager) {
        this.manager = manager;
    }

    private ServerSocket serverSocket;

    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    @Override
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
        //        clientSocket = serverSocket.accept();
        //        try {
        //            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //            String messageFromClient = in.readLine();
        //
        //            //Test
        //            joinPlayer(messageFromClient);
        //        } finally {
        //            clientSocket.close();
        //            in.close();
        //            out.close();
        //        }
        //    } finally {
        //        serverSocket.close();
        //    }
        //} catch (IOException e) {
        //    System.err.println(e);
        //}
    }

    @Override public void joinPlayer(String data){
        manager.joinPlayer(data);
    }

    @Override public void movePlayer(String data, String direction) { manager.movePlayer(data, direction); }
    @Override public void increaseCounterOfPlayer(String data) { manager.increaseCounterOfPlayer(data); }

    @Override
    public void answerOnJoinPlayer(String data) {
        //System.out.println("Message From Client: " + data);
        //try {
        //    out.write(manager.answerOnJoinPlayer() + '\n');
        //    out.flush();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    @Override
    public void answerOnMovePlayer(String data) { }

    @Override
    public void answerOnIncreaseCounterOfPlayer(String data) { }

    @Override
    public void sendEventPlayerLost(String data) { }
}
