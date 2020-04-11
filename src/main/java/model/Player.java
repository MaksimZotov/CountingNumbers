package model;

public class Player {
    private Manager manager;

    private String name;

    private Cell cell;
    private int counter;

    public Player(Manager manager, String name) {
        this.manager = manager;
        this.name = name;
    }

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
