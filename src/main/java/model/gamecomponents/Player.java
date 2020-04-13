package model.gamecomponents;

import model.manager.Manager;

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

    public void increaseCounter(int number) { counter += number; }

    public void decreaseCounter(int number) {
        counter -= number;
        if (counter <= 0)
            manager.playerLost(name);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public String getName() {
        return name;
    }
}
