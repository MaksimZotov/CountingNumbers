package model.game;

import java.io.Serializable;

public class Player implements Serializable {
    private final String name;
    private final GameState gameState;
    private final boolean isGreen;
    private Cell cell;
    private int score;
    private int countMove;

    public Player(String name, GameState gameState, boolean isGreen, Cell initialCell) {
        this.name = name;
        this.isGreen = isGreen;
        this.gameState = gameState;
        cell = initialCell;
        cell.setBlueThere(!isGreen);
        cell.setGreenThere(isGreen);
        countMove = 6;
        score = 10;
    }

    public void moveTo(Cell cell) {
        countMove--;
        if (isGreen) {
            this.cell.setGreenThere(false);
            cell.setGreenThere(true);
        }
        else {
            this.cell.setBlueThere(false);
            cell.setBlueThere(true);
        }
        score -= this.cell.getNumber();
        this.cell = cell;
        if (score <= 0)
            gameState.handleLoss(isGreen);
        if (countMove < 1)
            gameState.decideWhoLost();
    }

    public void increaseScore(int number) {
        countMove--;
        score += number;
        cell.setNumber(0);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        cell.setBlueThere(isGreen);
        cell.setGreenThere(isGreen);
    }

    public int getCountMove() { return countMove; }

    public boolean getIsGreen() {
        return getIsGreen();
    }

    public String getName() { return name; }

    public int getScore() { return score; }

    public Cell getCell() {
        return cell;
    }
}
