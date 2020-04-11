package controller;

import java.io.*;
import java.net.Socket;

public class Client {
    Server server = new Server();

    public void connectToServer(String name) {
        server.addPlayer(name);
    }

    private static Socket clientSocket;
    private static BufferedReader in; 
    private static BufferedWriter out; 

    public static void interactionWithServer() {
        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String someMessageToServer = "some message";
                out.write(someMessageToServer + "\n");
                out.flush();
                in.readLine();
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
