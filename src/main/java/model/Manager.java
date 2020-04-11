package model;

public class Manager {
    Field field;

    public void createField (int height, int width) {
        field = new Field(height, width);
    }

    public void movePlayer(Player player, Direction direction) {
        Cell currentCell = player.getCell();
        Cell nextCell;
        switch (direction) {
            case LEFT: nextCell = field.getCell(currentCell.getX() - 1, currentCell.getY()); break;
            case RIGHT: nextCell = field.getCell(currentCell.getX() + 1, currentCell.getY()); break;
            case UP: nextCell = field.getCell(currentCell.getX(), currentCell.getY() - 1); break;
            default: nextCell = field.getCell(currentCell.getX(), currentCell.getY() + 1);
        }
        player.decreaseCounter(currentCell.getNumber());
        player.setCell(nextCell);
    }

    public void increaseCounterOfPlayer(Player player) {
        Cell currentCell = player.getCell();
        player.increaseCounter(currentCell.getNumber());
        currentCell.setNumber(0);
    }

    public void playerLost(Player player) {

    }

    public void initPlayer(Player player, Cell cell, int counter) {
        player.initPlayer(cell, counter);
    }
}
