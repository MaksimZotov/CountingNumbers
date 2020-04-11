package model;

public class Player {
    private Manager manager;

    private String name;

    private Cell cell;
    private int counter;

    Player(Manager manager, String name) {
        this.manager = manager;
        this.name = name;
    }

    void initPlayer(Cell cell, int counter) {
        this.cell = cell;
        this.counter = counter;
    }

    public void move(Direction direction) {
        manager.movePlayer(this, direction);
    }

    public void increaseCounter() { manager.increaseCounterOfPlayer(this); }

    void increaseCounter(int number) { counter += number; }

    void decreaseCounter(int number) {
        counter -= number;
        if (counter <= 0)
            manager.playerLost(this);
    }

    void setCell(Cell cell) {
        this.cell = cell;
    }

    Cell getCell() {
        return cell;
    }
}
