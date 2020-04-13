package controller.obligations;

public interface Model {
    void handleDataFromClient(String data);
    void sendDataToClient(String data);
}
