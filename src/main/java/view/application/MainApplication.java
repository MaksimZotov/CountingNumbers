package view.application;

import controller.client.Client;
import controller.obligations.View;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.game.GameState;

import java.io.IOException;

public class MainApplication extends Application implements View {
    private Client client;
    private GameState gameState;
    private Rectangle[][] rectangles;
    private Text[][] texts;
    private Circle playerGreen;
    private Circle playerBlue;
    private Button mainButton;
    private TextField name;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        rectangles = new Rectangle[3][8];
        texts = new Text[3][8];
        playerGreen = new Circle();
        playerBlue = new Circle();

        playerGreen.setFill(Paint.valueOf("#00bf00"));
        playerBlue.setFill(Paint.valueOf("#3b86ff"));
        for (int i = 0; i < rectangles.length; i++) {
            int y = (i + 1) * 100;
            for (int j = 0; j < rectangles[i].length; j++) {
                int x = (j + 1) * 100;
                rectangles[i][j] = new Rectangle();
                rectangles[i][j].setArcHeight(15);
                rectangles[i][j].setArcWidth(15);
                rectangles[i][j].setFill(Paint.valueOf("#bfbfbf"));
                rectangles[i][j].setHeight(100);
                rectangles[i][j].setWidth(100);
                rectangles[i][j].setLayoutY(y);
                rectangles[i][j].setLayoutX(x);
                texts[i][j] = new Text();
                texts[i][j].setText("?");
                texts[i][j].setTextAlignment(TextAlignment.CENTER);
                texts[i][j].setFont(Font.font(30));
                texts[i][j].setFill(Paint.valueOf("#000000"));
                texts[i][j].setWrappingWidth(100);
                texts[i][j].setLayoutY(y + 60);
                texts[i][j].setLayoutX(x);
            }
        }
        playerGreen.setRadius(50);
        playerBlue.setRadius(50);
        playerGreen.setLayoutX(150);
        playerGreen.setCenterY(250);
        playerBlue.setLayoutX(850);
        playerBlue.setCenterY(250);

        AnchorPane pane = new AnchorPane();
        ObservableList<Node> children = pane.getChildren();
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = 0; j < rectangles[i].length; j++) {
                children.add(rectangles[i][j]);
                children.add(texts[i][j]);
            }
        }

        name = new TextField();
        name.setLayoutX(100);
        name.setLayoutY(420);

        mainButton = new Button();
        mainButton.setText("Connect to game");
        mainButton.setLayoutX(100);
        mainButton.setLayoutY(470);
        mainButton.setOnAction(e -> {
            try { handleClickOnMainButton(); } catch (IOException ex) { ex.printStackTrace(); }
        });

        children.addAll(playerBlue, playerGreen, mainButton, name);
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        scene.setOnKeyTyped(key -> {
            String keyCharacter = key.getCharacter();
            switch (keyCharacter) {
                case "w" : sendDataToServer("move up " + name.getText()); break;
                case "d" : sendDataToServer("move right " + name.getText()); break;
                case "s" : sendDataToServer("move down " + name.getText()); break;
                case "a" : sendDataToServer("move left " + name.getText());
            }
        });

        stage.setTitle("Counting Numbers");
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.show();
    }

    private void handleClickOnMainButton() throws IOException {
        if (client == null) {
            client = new Client(this);
            client.createConnection();
            name.setVisible(false);
            mainButton.setText("Quit the game");
            sendDataToServer("join " + name.getText());
        }
        else {
            sendDataToServer("exit " + name.getText());
            client.closeConnection();
            client = null;
            name.setVisible(true);
            mainButton.setText("Connect to game");
        }
    }

    private void updateField() {
        for (int i = 0; i < rectangles.length; i++)
            for (int j = 0; j < rectangles[i].length; j++) {
                if(gameState.getCell(j, i).getBlueThere()) {
                    playerBlue.setLayoutY(i * 100 - 100);
                    playerBlue.setLayoutX((j + 1) * 100 + 50);
                }
                if(gameState.getCell(j, i).getGreenThere()) {
                    playerGreen.setLayoutY(i * 100 - 100);
                    playerGreen.setLayoutX((j + 1) * 100 + 50);
                }
                texts[i][j].setText(String.valueOf(gameState.getCell(i, j).getNumber()));
            }
    }

    @Override
    public void sendDataToServer(Object data) { client.sendDataToServer(data); }

    @Override
    public void handleDataFromServer(Object data) {
        if (data instanceof GameState) {
            gameState = (GameState) data;
            updateField();
        }
    }
}
