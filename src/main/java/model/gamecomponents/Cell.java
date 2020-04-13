package model.gamecomponents;

public class Cell {
    private int x;
    private int y;
    private int number;

    public Cell(int x, int y, int number) {
        this.x = 0;
        this.y = 0;
        this.number = number;
    }

    public void setNumber(int number) { this.number = number; }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getNumber() { return number; }
}
