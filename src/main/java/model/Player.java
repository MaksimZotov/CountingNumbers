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

    void increaseCounter(int number) { counter += number; }

    void decreaseCounter(int number) {
        counter -= number;
        if (counter <= 0)
            manager.playerLost(name);
    }

    void setCell(Cell cell) {
        this.cell = cell;
    }

    Cell getCell() {
        return cell;
    }

    public String getName() {
        return name;
    }
}
