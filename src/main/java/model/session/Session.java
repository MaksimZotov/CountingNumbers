package model.session;

import controller.obligations.Model;
import controller.server.ClientHandler;
import controller.server.Server;
import model.game.GameState;

import java.util.ArrayList;

public class Session implements Model {
    private ArrayList<ClientHandler> clients;
    private final GameState gameState;
    private final Server server;
    private final int numberOfSession;

    public Session(Server server, GameState gameState, int numberOfSession) {
        clients = new ArrayList<>();
        this.server = server;
        this.gameState = gameState;
        this.numberOfSession = numberOfSession;
        gameState = new GameState();
    }

    public void addUser(ClientHandler user) { clients.add(user); }

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
                server.removeSession(numberOfSession);
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
}
