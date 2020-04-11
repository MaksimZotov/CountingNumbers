package model;

class Cell {
    private int x;
    private int y;
    private int number;

    Cell(int x, int y, int number) {
        this.x = 0;
        this.y = 0;
        this.number = number;
    }

    void setNumber(int number) { this.number = number; }

    int getX() { return x; }
    int getY() { return y; }
    int getNumber() { return number; }
}
