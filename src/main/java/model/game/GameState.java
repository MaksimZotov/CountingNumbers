package model.game;

import java.io.Serializable;
import java.util.Random;

public class GameState implements Serializable {
    private Random random = new Random();
    private Player playerGreen = null;
    private Player playerBlue = null;
    private String messageToClient;
    private Cell[][] cells;
    private boolean messageToClientInitialized = false;

    public void addPlayer(String name) {
        if (playerGreen == null) {
            messageToClient = "Waiting for other player";
            playerGreen = new Player(name, this, true, new Cell(-1, -1, -1));
            return;
        }
        if (playerBlue == null) {
            if (playerGreen.getName().equals(name))
                throw new IllegalArgumentException("Player with name \"" + name +"\" already exists");
            createField();
            playerGreen.setCell(getCell(0, 1));
            cells[1][0].setNumber(0);
            playerBlue = new Player(name, this, false, getCell(7, 1));
            cells[1][7].setNumber(0);
            messageToClient = playerGreen.getCountMove() + " : " + playerBlue.getCountMove();
            return;
        }
        throw new IllegalStateException("All players are already in the game");
    }

    public void movePlayer(String name, String direction) {
        if (playerBlue == null || playerGreen == null)
            return;
        Player player = findPlayer(name);
        Cell currentCell = player.getCell();
        int x = currentCell.getX();
        int y = currentCell.getY();
        switch (direction) {
            case "up": y--; break;
            case "down": y++; break;
            case "left": x--; break;
            case "right": x++; break;
            default: throw new IllegalArgumentException("Incorrect direction");
        }
        if (y >= 0 && y <= 2 && x >= 0 && x <= 7)
            player.moveTo(cells[y][x]);
        setMessageToClient();
    }

    public void increaseScore(String name) {
        if (playerBlue == null || playerGreen == null)
            return;
        Player player = findPlayer(name);
        player.increaseScore(player.getCell().getNumber());
        setMessageToClient();
    }

    public String getMessageToClient() { return messageToClient; }

    public Cell getCell(int x, int y) { return cells[y][x]; }

    public void onExit() { messageToClient = "The other player leave the game"; }

    public int getScoreGreenPlayer() { return playerGreen.getScore(); }

    public int getScoreBluePlayer() { return playerBlue.getScore(); }

    void handleLoss(boolean greenLost) {
        String msg = greenLost ? "green player lost "  + getScoreGreenPlayer() + " : " + getScoreBluePlayer() :
                "blue player lost " + getScoreGreenPlayer() + " : " + getScoreBluePlayer();
        messageToClient = "The " + msg;
        messageToClientInitialized = true;
        createNewGame();
    }

    void decideWhoLost() {
        if (playerGreen.getScore() == playerBlue.getScore()) {
            messageToClientInitialized = true;
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
            for (int j = 0; j < 4; j++)
                cells[i][j] = new Cell(j, i, random.nextInt(10));
        }
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                cells[i][j + 4] = new Cell(j + 4, i, cells[2 - i][3 - j].getNumber());
    }

    private void createNewGame() {
        createField();
        playerGreen = new Player(playerGreen.getName(), this, true, getCell(0, 1));
        playerBlue = new Player(playerBlue.getName(), this, false, getCell(7, 1));
        cells[1][0].setNumber(0);
        cells[1][7].setNumber(0);
    }

    private Player findPlayer(String name) {
        Player player = null;
        if (playerGreen != null && playerGreen.getName().equals(name))
            player = playerGreen;
        if (playerBlue != null && playerBlue.getName().equals(name))
            player = playerBlue;
        if (player == null)
            throw new IllegalArgumentException("Player with name \"" + name + "\" does not exist");
        return player;
    }

    private void setMessageToClient() {
        if (messageToClientInitialized)
            messageToClientInitialized = false;
        else
            messageToClient = playerGreen.getCountMove() + " : " + playerBlue.getCountMove();
    }
}
