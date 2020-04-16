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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.game.GameState;
import org.w3c.dom.css.Rect;

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
    private Text scoreGreen;
    private Text scoreBlue;
    private Text messageFromServer;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        rectangles = new Rectangle[3][8];
        texts = new Text[3][8];
        playerGreen = new Circle();
        playerBlue = new Circle();

        playerGreen.setFill(Paint.valueOf("#00bf00"));
        playerBlue.setFill(Paint.valueOf("#3b86ff"));
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = 0; j < rectangles[i].length; j++) {
                createRectangleInTheArray(j, i);
                createTextInTheArray(j, i);
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

        scoreGreen = createText();
        scoreGreen.setLayoutX(100);
        scoreGreen.setLayoutY(260);
        scoreGreen.setText("$");
        scoreBlue = createText();
        scoreBlue.setLayoutX(800);
        scoreBlue.setLayoutY(260);
        scoreBlue.setText("$");

        messageFromServer = new Text();
        messageFromServer.setLayoutX(100);
        messageFromServer.setLayoutY(75);
        messageFromServer.setText("Welcome!");
        messageFromServer.setFont(Font.font(40));

        children.addAll(playerBlue, playerGreen, mainButton, name, scoreGreen, scoreBlue, messageFromServer);
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.setTitle("Counting Numbers");
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.show();

        mainButton.setOnAction(e -> { try { handleClickOnMainButton(); } catch (IOException ex) { ex.printStackTrace(); } });
        scene.setOnKeyTyped(key -> handlePressKey(key));
    }

    private void handlePressKey(KeyEvent key) {
        String keyCharacter = key.getCharacter();
        switch (keyCharacter) {
            case "w" : sendDataToServer("move " + name.getText() + " up"); break;
            case "d" : sendDataToServer("move " + name.getText() + " right"); break;
            case "s" : sendDataToServer("move " + name.getText() + " down"); break;
            case "a" : sendDataToServer("move " + name.getText() + " left"); break;
            case "f" : sendDataToServer("increase " + name.getText());
        }
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

    private Text createText() {
        Text text = new Text();
        text.setText("?");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(30));
        text.setFill(Paint.valueOf("#000000"));
        text.setWrappingWidth(100);
        return text;
    }
    private void createTextInTheArray(int x, int y) {
        Text text = createText();
        text.setLayoutY((y + 1) * 100 + 60);
        text.setLayoutX((x + 1) * 100);
        texts[y][x] = text;
    }

    private void createRectangleInTheArray(int x, int y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        rectangle.setFill(Paint.valueOf("#bfbfbf"));
        rectangle.setHeight(100);
        rectangle.setWidth(100);
        rectangle.setLayoutY((y + 1) * 100);
        rectangle.setLayoutX((x + 1) * 100);
        rectangles[y][x] = rectangle;
    }

    private void updateField() {
        String msg = gameState.getMessageToClient();
        messageFromServer.setText(msg);
        if (msg.equals("Waiting for other player"))
            return;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 8; j++) {
                if(gameState.getCell(j, i).getBlueThere()) {
                    playerBlue.setLayoutY(i * 100 - 100);
                    playerBlue.setLayoutX((j + 1) * 100 + 50);
                    scoreBlue.setLayoutX((j + 1) * 100);
                    scoreBlue.setLayoutY((i + 1) * 100 +  60);
                    scoreBlue.setText(String.valueOf(gameState.getScoreBluePlayer()));
                }
                if(gameState.getCell(j, i).getGreenThere()) {
                    playerGreen.setLayoutY(i * 100 - 100);
                    playerGreen.setLayoutX((j + 1) * 100 + 50);
                    scoreGreen.setLayoutX((j + 1) * 100);
                    scoreGreen.setLayoutY((i + 1) * 100 +  60);
                    scoreGreen.setText(String.valueOf(gameState.getScoreGreenPlayer()));
                }
                texts[i][j].setText(String.valueOf(gameState.getCell(j, i).getNumber()));
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
