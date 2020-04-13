package controller.client;

import controller.obligations.View;

import java.io.*;
import java.net.Socket;

public class Client {
    private View view;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private ClientSender sender;
    private ClientReader reader;

    public Client(View view) {
        this.view = view;
    }

    public BufferedReader getIn() { return in; }

    public void createConnection() throws IOException {
        clientSocket = new Socket("localhost", 6666);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        System.out.println("Client was launched");
        sender = new ClientSender(out);
        reader = new ClientReader(this);
    }

    public void closeConnection() throws IOException {
        clientSocket.close();
        in.close();
        out.close();
    }

    public void sendDataToServer(String data) throws IOException { sender.sendDataToServer(data); }

    public void handleDataFromServer(String data) {
        view.handleDataFromServer(data);
    }
}
