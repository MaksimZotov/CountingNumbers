package controller.client;

import controller.obligations.View;

import java.io.*;
import java.net.Socket;

public class Client {
    private View view;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ClientSender sender;
    private ClientReader reader;

    public Client(View view) { this.view = view; }

    public void createConnection() throws IOException {
        clientSocket = new Socket("localhost", 6666);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        sender = new ClientSender(out);
        reader = new ClientReader(this);
    }

    public void closeConnection() throws IOException {
        sender.sendDataToServer("exit");
        clientSocket.close();
        in.close();
        out.close();
    }

    public void sendDataToServer(Object data) { sender.sendDataToServer(data); }

    void handleDataFromServer(Object data) { view.handleDataFromServer(data); }

    ObjectInputStream getIn() { return in; }
}
