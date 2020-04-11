package view;

import controller.Client;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class MainApplication extends Application {
    private static Client client = new Client();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Text text = new Text("Hi! Enter your name:");
        TextField name = new TextField();
        Button buttonConnect = new Button();

        text.setLayoutY(130);
        text.setLayoutX(150);
        name.setLayoutY(155);
        name.setLayoutX(132);
        buttonConnect.setLayoutY(200);
        buttonConnect.setLayoutX(150);
        buttonConnect.setText("Connect to game");

        Group group = new Group(text, name, buttonConnect);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("Counting Numbers");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();

        buttonConnect.setOnAction(e -> {
            client.connectToServer(name.getText());
            createSceneOfGame(stage);
        });
    }

    private void createSceneOfGame(Stage stage) {
        Text messageFromServer = new Text("Message from the server will there <-");
        TextField messageToServer = new TextField();
        Button sendMessage = new Button();

        messageFromServer.setLayoutY(130);
        messageFromServer.setLayoutX(100);
        messageToServer.setLayoutY(155);
        messageToServer.setLayoutX(100);
        sendMessage.setLayoutY(200);
        sendMessage.setLayoutX(100);
        sendMessage.setText("Send Message");

        Group group = new Group(messageFromServer, messageToServer, sendMessage);
        Scene scene = new Scene(group);
        stage.setScene(scene);
    }
}
