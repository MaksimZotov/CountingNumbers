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
    private final int heightField = 3;
    private final int widthField = 8;
    private final int greenStartX = 0;
    private final int greenStartY = 1;
    private final int blueStartX = 7;
    private final int blueStartY = 1;

    public void addPlayer(int id) {
        if (playerGreen == null) {
            messageToClient = "Waiting for other player";
            playerGreen = new Player(id, this, true, new Cell(-1, -1, -1));
            return;
        }
        if (playerBlue == null) {
            if (playerGreen.getId() == id)
                throw new IllegalArgumentException("Player with id \"" + id +"\" already exists");
            createField();
            playerGreen.setCell(getCell(greenStartX, greenStartY));
            cells[greenStartY][greenStartX].setNumber(0);
            playerBlue = new Player(id, this, false, getCell(blueStartX, blueStartY));
            cells[blueStartY][blueStartX].setNumber(0);
            messageToClient = playerGreen.getCountMove() + " : " + playerBlue.getCountMove();
            return;
        }
        throw new IllegalStateException("All players are already in the game");
    }

    public void movePlayer(int id, String direction) {
        if (playerBlue == null || playerGreen == null)
            return;
        Player player = findPlayer(id);
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
        if (y >= 0 && y < heightField && x >= 0 && x < widthField)
            player.moveTo(cells[y][x]);
        setMessageToClient();
    }

    public void increaseScore(int id) {
        if (playerBlue == null || playerGreen == null)
            return;
        Player player = findPlayer(id);
        player.increaseScore(player.getCell().getNumber());
        setMessageToClient();
    }

    public String getMessageToClient() { return messageToClient; }

    public Cell getCell(int x, int y) { return cells[y][x]; }

    public void onExit(int id) {
        String player = null;
        if (playerGreen != null && playerGreen.getId() == id)
            player = "green";
        if (playerBlue != null && playerBlue.getId() == id)
            player = "blue";
        messageToClient = "exit The " + player + " player left the game";
    }

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
        cells = new Cell[heightField][];
        int widthDiv2 = widthField / 2;
        for (int i = 0; i < heightField; i++) {
            cells[i] = new Cell[widthField];
            for (int j = 0; j < widthDiv2; j++)
                cells[i][j] = new Cell(j, i, random.nextInt(10));
        }
        for (int i = 0; i < heightField; i++)
            for (int j = 0; j < widthDiv2; j++)
                cells[i][j + widthDiv2] = new Cell(j + widthDiv2, i, cells[heightField - 1 - i][widthDiv2 - 1 - j].getNumber());
    }

    private void createNewGame() {
        createField();
        playerGreen = new Player(playerGreen.getId(), this, true, getCell(greenStartX, greenStartY));
        playerBlue = new Player(playerBlue.getId(), this, false, getCell(blueStartX, blueStartY));
        cells[greenStartY][greenStartX].setNumber(0);
        cells[blueStartY][blueStartX].setNumber(0);
    }

    private Player findPlayer(int id) {
        Player player = null;
        if (playerGreen != null && playerGreen.getId() == id)
            player = playerGreen;
        if (playerBlue != null && playerBlue.getId() == id)
            player = playerBlue;
        if (player == null)
            throw new IllegalArgumentException("Player with id \"" + id + "\" does not exist");
        return player;
    }

    private void setMessageToClient() {
        if (messageToClientInitialized)
            messageToClientInitialized = false;
        else
            messageToClient = playerGreen.getCountMove() + " : " + playerBlue.getCountMove();
    }
}
