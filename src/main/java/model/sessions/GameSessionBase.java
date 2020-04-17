package model.sessions;

import controller.obligations.Session;
import controller.obligations.SessionManager;
import controller.server.ClientHandler;
import model.game.GameState;

import java.util.ArrayList;
import java.util.Iterator;

public class GameSessionBase implements Session {
    private ArrayList<ClientHandler> clients;
    private final GameState gameState;
    private final SessionManager sessionManager;
    private final int sessionIndex;

    public GameSessionBase(SessionManager sessionManager, GameState gameState, int sessionIndex) {
        clients = new ArrayList<>();
        this.sessionManager = sessionManager;
        this.gameState = gameState;
        this.sessionIndex = sessionIndex;
        gameState = new GameState();
    }

    @Override
    public void handleDataFromClient(Object data, int id) {
        String[] dataString = ((String) data).split(" ");
        switch (dataString[0]) {
            case "join" : gameState.addPlayer(id); break;
            case "move" : gameState.movePlayer(id, dataString[1]); break;
            case "increase" : gameState.increaseScore(id); break;
            case "exit" : {
                gameState.onExit(id);
                sendDataToClient(gameState, id);
                sessionManager.removeSession(sessionIndex);
                sendDataToClient(gameState, id);
                return;
            }
        }
        sendDataToClient(gameState, id);
    }

    @Override
    public void sendDataToClient(Object data, int id) {
        for (ClientHandler item : clients)
            item.sendDataToClient(data);
    }

    @Override
    public void addClient(ClientHandler client) { clients.add(client); }

    @Override
    public ClientHandler getClient(int clientId) {
        for (ClientHandler client : clients)
            if(client.getClientId() == clientId)
                return client;
        return null;
    }

    @Override
    public void removeClient(int clientId) {
        Iterator iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientHandler client = (ClientHandler) iterator.next();
            if (client.getClientId() == clientId) {
                iterator.remove();
                return;
            }
        }
    }
}
