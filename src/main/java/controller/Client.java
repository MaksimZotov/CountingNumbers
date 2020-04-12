package controller;

import controller.obligations.ClientObligations;

import java.io.*;
import java.net.Socket;

public class Client implements ClientObligations {
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    @Override
    public void joinPlayer(String name) {
        try {
            clientSocket = new Socket("localhost", 6666);
            System.out.println("Client was launched");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String messageToServer = name;
            System.out.println(messageToServer);
            out.write(messageToServer + '\n');
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String answerOnJoinPlayer(String name) {
        String answerFromServer = null;
        try {
            try {
                answerFromServer = in.readLine();
                System.out.println(answerFromServer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return answerFromServer;
    }


    @Override public void movePlayer(String name, String direction) { }
    @Override public void increaseCounterOfPlayer(String name) { }

    @Override public void answerOnMovePlayer(String name) { }
    @Override public void answerOnIncreaseCounterOfPlayer(String name) { }
    @Override public void getEventPlayerLost(String name) { }
}
