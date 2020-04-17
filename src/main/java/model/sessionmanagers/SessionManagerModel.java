package model.sessionmanagers;

import controller.obligations.SessionManager;
import controller.server.ClientHandler;
import model.game.GameState;
import model.sessions.GameSessionBase;

import java.net.Socket;
import java.util.ArrayList;

public class SessionManagerModel implements SessionManager {
    private ArrayList<GameSessionBase> sessions = new ArrayList<>();
    private int sessionIndex;
    private int usersCount;

    @Override
    public void addSocket(Socket socket) {
        if (usersCount % 2 == 0) {
            sessions.add(new GameSessionBase(this, new GameState(), sessionIndex));
            sessions.get(sessionIndex).addClient(new ClientHandler(socket, sessions.get(sessionIndex), 0));
        }
        else {
            sessions.get(sessionIndex).addClient(new ClientHandler(socket, sessions.get(sessionIndex), 1));
            sessionIndex++;
        }
        usersCount++;
    }

    @Override
    public void removeSession(int sessionIndex) {
        sessions.remove(sessionIndex);
        if (usersCount % 2 == 0) {
            usersCount -= 2;
            this.sessionIndex--;
        }
        else
            usersCount--;
    }
}
