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
        cell.setBlueThere(isGreen);
        cell.setGreenThere(isGreen);
        countMove = 0;
    }

    public void increaseScore(int number) {
        score += number;
    }

    public void moveTo(Cell cell) {
        countMove++;
        if (isGreen) {
            this.cell.setGreenThere(false);
            cell.setGreenThere(true);
        }
        else {
            this.cell.setBlueThere(false);
            cell.setBlueThere(true);
        }
        score -= cell.getNumber();
        this.cell = cell;
        if (score <= 0)
            gameState.handleLoss(isGreen);
        if (countMove > 3)
            gameState.decideWhoLost();
    }

    public boolean getIsGreen() {
        return getIsGreen();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Cell getCell() {
        return cell;
    }
}
