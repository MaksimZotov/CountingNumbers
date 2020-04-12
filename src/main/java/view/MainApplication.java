package view;

import controller.*;
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

public class MainApplication extends Application {
    private Client client;
    private Stage stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
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

    @FXML
    private Text messageFromServer;

    @FXML
    private TextField name;

    @FXML
    private Button buttonConnect;

    @FXML
    private void sendMessage(ActionEvent event) {
        client = new Client();
        String playerName = name.getText();
        client.joinPlayer(playerName);
        messageFromServer.setText(client.answerOnJoinPlayer(playerName));
    }
}
