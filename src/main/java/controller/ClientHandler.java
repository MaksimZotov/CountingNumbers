package controller;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Server server;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {
        String data;
        try {
            while (true) {
                data = in.readLine();
                server.joinPlayer(data);
                for(ClientHandler item : server.getClientHandlers())
                    item.sendData(server.getManager().answerOnJoinPlayer() + '\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData(String data) {
        try {
            out.write(data + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
