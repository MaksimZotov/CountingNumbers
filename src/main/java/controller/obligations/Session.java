package controller.obligations;

import controller.server.ClientHandler;

public interface Session {
    void handleDataFromClient(Object data, int clientId);
    void sendDataToClient(Object data, int clientId);
    void addClient(ClientHandler clientHandler);
    void removeClient(int clientId);
    ClientHandler getClient(int clientId);
}
