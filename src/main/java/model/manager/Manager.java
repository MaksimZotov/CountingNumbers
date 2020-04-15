package model.manager;

import controller.server.ClientHandler;
import controller.server.Server;
import controller.obligations.Model;
import model.game.GameState;

public class Manager implements Model {
    private Server server;
    private GameState gameState;

    public void main() {
        gameState = new GameState();
        server = new Server(this);
        server.main();
    }

    @Override
    public void handleDataFromClient(Object data) {
        String[] dataString = ((String) data).split(" ");
        switch (dataString[0]) {
            case "join" : gameState.addPlayer(dataString[1]); break;
            case "move" : gameState.movePlayer(dataString[1], dataString[2]); break;
            case "increase" : gameState.increaseScore(dataString[1]); break;
            case "exit" : gameState.onExit();
        }
        sendDataToClient(gameState);
    }

    @Override
    public void sendDataToClient(Object data) {
        for (ClientHandler item : server.getClientHandlers())
            item.sendDataToClient(data);
    }
}
