package controller.server;

import model.session.Session;
import model.game.GameState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Session> sessions = new ArrayList<>();
    private int sessionIndex;
    private int usersCount;

    public void main() {
        sessionIndex = 0;
        usersCount = 0;
        try {
            try {
                serverSocket = new ServerSocket(6666);
                while (true) {
                    Socket socket = serverSocket.accept();
                    if (usersCount % 2 == 0) {
                        sessions.add(new Session(this, new GameState(), sessionIndex));
                        sessions.get(sessionIndex).addUser(new ClientHandler(socket, sessions.get(sessionIndex), 0));
                    }
                    else {
                        sessions.get(sessionIndex).addUser(new ClientHandler(socket, sessions.get(sessionIndex), 1));
                        sessionIndex++;
                    }
                    usersCount++;
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSession(int index) {
        sessions.remove(index);
        if (usersCount % 2 == 0) {
            usersCount -= 2;
            sessionIndex--;
        }
        else
            usersCount--;
    }
}
