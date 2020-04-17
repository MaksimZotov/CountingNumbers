package controller.obligations;

public interface Model {
    void handleDataFromClient(Object data, int clientId);
    void sendDataToClient(Object data, int clientId);
}
