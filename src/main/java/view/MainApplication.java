package view;

import controller.Client;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private Client client;
    private Stage stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        // ---
        //Parent root = null;
        //try {
        //    root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //stage.setScene(new Scene(root));
        // ---

        // ---
        Rectangle[][] rectangles = new Rectangle[3][8];
        Text[][] texts = new Text[3][8];
        Circle playerGreen = new Circle();
        Circle playerBlue = new Circle();
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
                texts[i][j].setText("n");
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
        children.addAll(playerBlue, playerGreen);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        scene.setOnKeyTyped(key -> {
            String keyCharecter = key.getCharacter();
            switch (keyCharecter) {
                case "w" : playerGreen.setLayoutY(playerGreen.getLayoutY() - 100); break;
                case "d" : playerGreen.setLayoutX(playerGreen.getLayoutX() + 100); break;
                case "s" : playerGreen.setLayoutY(playerGreen.getLayoutY() + 100); break;
                case "a" : playerGreen.setLayoutX(playerGreen.getLayoutX() - 100);
            }
        });
        // ---

        stage.setTitle("Counting Numbers");
        stage.setWidth(1000);
        stage.setHeight(600);
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
