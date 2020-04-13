package model.manager;

import controller.server.ClientHandler;
import controller.server.Server;
import controller.obligations.Model;
import model.gamecomponents.Cell;
import model.gamecomponents.Field;
import model.gamecomponents.Player;

import java.util.ArrayList;

public class Manager implements Model {
    private Server server;
    private Field field;
    private ArrayList<Player> players = new ArrayList<>();

    public void main() {
        server = new Server(this);
        server.main();
    }

    public void createField (int height, int width) {
        field = new Field(height, width);
    }

    private Player findPlayer(String name) {
        Player player = null;
        for (Player item : players)
            if (item.getName().equals(name)) {
                player = item;
                break;
            }
        if (player == null)
            throw new IllegalStateException("Player with name \""+ name + "\" does not exist");
        return player;
    }

    public void playerLost(String name) { }

    private void initPlayer(Player player, Cell cell, int counter) {
        player.initPlayer(cell, counter);
    }

    public void joinPlayer(String name) {
        players.add(new Player(this, name));
        if (players.size() == 2) {
            createField(8, 3);
            initPlayer(players.get(0), new Cell(1, 0, 0), 10);
            initPlayer(players.get(1), new Cell(1, 7, 0), 10);
        }
    }

    private String answerOnJoinPlayer() {
        String string = "";
        for (Player item : players)
            string += item.getName();
        return string + " Test";
    }
    public void movePlayer(String name, String direction) {
        Player player = findPlayer(name);
        Cell currentCell = player.getCell();
        Cell nextCell;
        switch (direction) {
            case "left": nextCell = field.getCell(currentCell.getX() - 1, currentCell.getY()); break;
            case "right": nextCell = field.getCell(currentCell.getX() + 1, currentCell.getY()); break;
            case "up": nextCell = field.getCell(currentCell.getX(), currentCell.getY() - 1); break;
            case "down": nextCell = field.getCell(currentCell.getX(), currentCell.getY() + 1); break;
            default: throw new IllegalArgumentException("Incorrect direction");
        }
        player.decreaseCounter(currentCell.getNumber());
        player.setCell(nextCell);
    }

    public void increaseCounterOfPlayer(String name) {
        Player player = findPlayer(name);
        Cell currentCell = player.getCell();
        player.increaseCounter(currentCell.getNumber());
        currentCell.setNumber(0);
    }

    @Override
    public void handleDataFromClient(String data) {
        joinPlayer(data);
        sendDataToClient(answerOnJoinPlayer());
    }

    @Override
    public void sendDataToClient(String data) {
        for (ClientHandler item : server.getClientHandlers())
            item.sendDataToClient(data);
    }
}
