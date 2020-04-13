package controller.obligations;

public interface Model {
    void handleDataFromClient(Object data);
    void sendDataToClient(Object data);
}
