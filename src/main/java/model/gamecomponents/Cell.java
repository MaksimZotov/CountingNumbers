package model.gamecomponents;

import java.io.Serializable;

public class Cell implements Serializable {
    private int x;
    private int y;
    private int number;
    private boolean playerGreenThere;
    private boolean playerBlueThere;

    public Cell(int y, int x, int number) {
        this.y = y;
        this.x = x;
        this.number = number;
    }

    public void setNumber(int number) { this.number = number; }
    public void setPlayerGreenThere(boolean playerGreenThere) { this.playerGreenThere = playerGreenThere; }
    public void setPlayerBlueThere(boolean playerBlueThere) { this.playerBlueThere = playerBlueThere; }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getNumber() { return number; }
    public boolean getPlayerGreenThere() { return playerGreenThere; }
    public boolean getPlayerBlueThere () { return playerBlueThere; }
}
