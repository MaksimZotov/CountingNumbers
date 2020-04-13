package controller.obligations;

public interface View {
    void sendDataToServer(String data);
    void handleDataFromServer(String data);
}
