package model;

public class Player {
    private Manager manager;

    private Cell cell;
    private int counter;

    public Player(Manager manager) { this.manager = manager; }

    public void initPlayer(Cell cell, int counter) {
        this.cell = cell;
        this.counter = counter;
    }

    public void move(Direction direction) {
        manager.movePlayer(this, direction);
    }

    public void increaceCounter() { manager.increaseCounterOfPlayer(this); }

    public void increaseCounter(int number) { counter += number; }

    public void decreaseCounter(int number) {
        counter -= number;
        if (counter <= 0)
            manager.playerLost(this);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
