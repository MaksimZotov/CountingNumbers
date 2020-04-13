package view.application;

import controller.client.Client;
import controller.obligations.View;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application implements View {
    private Client client;
    private Stage stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Counting Numbers");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();
    }

    @Override
    public void sendDataToServer(Object data) { client.sendDataToServer(data); }

    @Override
    public void handleDataFromServer(Object data) { messageFromServer.setText((String) data); }

    @FXML
    private Text messageFromServer;

    @FXML
    private TextField name;

    @FXML
    private Button buttonConnect;

    @FXML
    private void sendMessage(ActionEvent event) throws IOException {
        if (client == null) {
            client = new Client(this);
            client.createConnection();
        }
        String playerName = name.getText();
        sendDataToServer(playerName);
    }
}
