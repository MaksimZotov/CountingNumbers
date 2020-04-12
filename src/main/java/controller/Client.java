package controller;

import controller.obligations.ApplicationObligations;
import controller.obligations.ClientObligations;

import java.io.*;
import java.net.Socket;

public class Client implements ClientObligations {
    private ApplicationObligations application;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    public Client(ApplicationObligations application) {
        this.application = application;
    }

    @Override
    public void main(String data) {
        try {
            try {
                clientSocket = new Socket("localhost", 6666);
                System.out.println("Client was launched");
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String messageToServer = data;
                System.out.println(messageToServer);
                out.write(messageToServer + '\n');
                out.flush();

                String answerFromServer = in.readLine();
                System.out.println(answerFromServer);

                application.answerOnJoinPlayer(answerFromServer);
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
    }

    @Override
    public void joinPlayer(String data) {
        try {
            clientSocket = new Socket("localhost", 6666);
            System.out.println("Client was launched");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String messageToServer = data;
            System.out.println(messageToServer);
            out.write(messageToServer + '\n');
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void answerOnJoinPlayer(String data) {

    }


    @Override public void movePlayer(String data, String direction) { }
    @Override public void increaseCounterOfPlayer(String data) { }

    @Override public void answerOnMovePlayer(String data) { }
    @Override public void answerOnIncreaseCounterOfPlayer(String data) { }
    @Override public void getEventPlayerLost(String data) { }
}
