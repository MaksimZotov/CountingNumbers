package controller;

import java.io.*;
import java.net.Socket;

public class Client {
    public void joinPlayer(String name) { }
    public void movePlayer(String name, String direction) { }
    public void increaseCounterOfPlayer(String name) { }

    private static Socket clientSocket;
    private static BufferedReader in; 
    private static BufferedWriter out; 

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                System.out.println("Client was launched");
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String someMessageToServer = "Some message";
                System.out.println(someMessageToServer);
                out.write(someMessageToServer + "\n");
                out.flush();
                String answerFromServer = in.readLine();
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
    }
}
