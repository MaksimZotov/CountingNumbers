package model.game;

import java.io.Serializable;

public class Cell implements Serializable {
    private final int x;
    private final int y;
    private int number;
    private boolean greenThere;
    private boolean blueThere;

    public Cell(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setGreenThere(boolean there) { greenThere = there; }

    public void setBlueThere(boolean there) {
        blueThere = there;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public boolean getGreenThere() {
        return greenThere;
    }

    public boolean getBlueThere() {
        return blueThere;
    }
}
