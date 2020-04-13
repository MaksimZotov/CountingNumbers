package controller.obligations;

public interface View {
    void sendDataToServer(Object data);
    void handleDataFromServer(Object data);
}
