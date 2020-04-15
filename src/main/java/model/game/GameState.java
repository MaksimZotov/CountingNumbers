package model.game;

import java.io.Serializable;
import java.util.Random;

public class GameState implements Serializable {
    private Random random;
    private Player playerGreen = null;
    private Player playerBlue = null;
    private String messageToClient;
    private Cell[][] cells;

    public void addPlayer(String name) {
        if (playerGreen == null) {
            playerGreen = new Player(name, this, true, getCell(0, 1));
            messageToClient = "Waiting for other player";
            return;
        }
        if (playerBlue == null) {
            if (playerGreen.getName().equals(name))
                throw new IllegalArgumentException("Player with name \"" + name +"\" already exists");
            playerBlue = new Player(name, this, false, getCell(7, 1));
            createField();
            messageToClient = "All players are in the game";
            return;
        }
        throw new IllegalStateException("All players are already in the game");
    }

    public void movePlayer(String name, String direction) {
        Player player = findPlayer(name);
        Cell currentCell = player.getCell();
        int x = currentCell.getX();
        int y = currentCell.getY();
        switch (direction) {
            case "up": player.moveTo(cells[y - 1][x]); break;
            case "down": player.moveTo(cells[y + 1][x]); break;
            case "left": player.moveTo(cells[y][x - 1]); break;
            case "right": player.moveTo(cells[y][x + 1]); break;
            default: throw new IllegalArgumentException("Incorrect direction");
        }
    }

    public void increaseScore(String name) {
        Player player = findPlayer(name);
        player.increaseScore(player.getCell().getNumber());
    }

    public String getMessageToClient() {
        return messageToClient;
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public void onExit() {
        messageToClient = "The other player has left the game";
    }

    void handleLoss(boolean greenLost) {
        String msg = greenLost ? "green player lost" : "blue player lost";
        messageToClient = "The " + msg;
        createNewGame();
    }

    void decideWhoLost() {
        if (playerGreen.getScore() == playerBlue.getScore()) {
            messageToClient = "Draw";
            createNewGame();
            return;
        }
        boolean greenLost = playerGreen.getScore() < playerBlue.getScore();
        handleLoss(greenLost);
    }

    private void createField() {
        cells = new Cell[3][];
        for (int i = 0; i < 3; i++) {
            cells[i] = new Cell[8];
            for (int j = 0; j < 8; j++)
                cells[i][j] = new Cell(j, i, random.nextInt(10));
        }
    }

    private void createNewGame() {
        createField();
        playerGreen.moveTo(cells[1][0]);
        playerBlue.moveTo(cells[1][7]);
    }

    private Player findPlayer(String name) {
        Player player = null;
        if (playerGreen.getName().equals(name))
            player = playerGreen;
        if (playerBlue.getName().equals(name))
            player = playerBlue;
        if (player == null)
            throw new IllegalArgumentException("Player with name \"" + name + "\" does not exist");
        return player;
    }
}
