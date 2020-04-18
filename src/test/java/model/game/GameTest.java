package model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void test() {
        int prevCellNumber;
        int prevScore;

        int greenId = 0;
        int blueId = 1;

        GameState gameState = new GameState();
        gameState.addPlayer(0);
        gameState.addPlayer(1);

        assertTrue(gameState.getCell(0, 1).getGreenThere());
        assertFalse(gameState.getCell(7, 1).getGreenThere());

        assertFalse(gameState.getCell(0, 1).getBlueThere());
        assertTrue(gameState.getCell(7, 1).getBlueThere());

        gameState.movePlayer(greenId, "up");
        assertFalse(gameState.getCell(0, 1).getGreenThere());
        assertTrue(gameState.getCell(0, 0).getGreenThere());

        gameState.movePlayer(blueId, "right");
        assertTrue(gameState.getCell(7, 1).getBlueThere());

        gameState.movePlayer(blueId, "left");
        assertTrue(gameState.getCell(6, 1).getBlueThere());
        assertFalse(gameState.getCell(7, 1).getBlueThere());

        prevCellNumber = gameState.getCell(6, 1).getNumber();
        prevScore = gameState.getScoreBluePlayer();
        gameState.movePlayer(blueId, "right");
        assertEquals(prevScore - prevCellNumber, gameState.getScoreBluePlayer());

        gameState.movePlayer(greenId, "up");
        assertTrue(gameState.getCell(0, 0).getGreenThere());
        assertFalse(gameState.getCell(0, 1).getGreenThere());

        prevCellNumber = gameState.getCell(0, 0).getNumber();
        prevScore = gameState.getScoreGreenPlayer();
        gameState.increaseScore(greenId);
        assertEquals(prevScore + prevCellNumber, gameState.getScoreGreenPlayer());
    }
}
