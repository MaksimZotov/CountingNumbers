package model;

import controller.obligations.ManagerObligations;
import controller.Server;
import controller.obligations.ServerObligations;

import java.util.ArrayList;

public class Manager implements ManagerObligations {
    private static ServerObligations server;
    public static void main(String[] args) {
        Manager manager = new Manager();
        server = new Server(manager);
        manager.setServer(server);
        server.main();
    }
    private void setServer(ServerObligations server) { Manager.server = server; }


    private Field field;
    private ArrayList<Player> players = new ArrayList<>();

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

    public void playerLost(String name) { server.sendEventPlayerLost(name); }

    private void initPlayer(Player player, Cell cell, int counter) {
        player.initPlayer(cell, counter);
    }

    @Override
    public void joinPlayer(String name) {
        players.add(new Player(this, name));
        server.answerOnJoinPlayer(name);
        if (players.size() == 2) {
            createField(8, 3);
            initPlayer(players.get(0), new Cell(1, 0, 0), 10);
            initPlayer(players.get(1), new Cell(1, 7, 0), 10);
        }
    }

    @Override
    public String answerOnJoinPlayer() {
        String string = "";
        for (Player item : players)
            string += item.getName();
        return string + " Test";
    }

    @Override
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
        server.answerOnMovePlayer(name);
    }

    @Override
    public void increaseCounterOfPlayer(String name) {
        Player player = findPlayer(name);
        Cell currentCell = player.getCell();
        player.increaseCounter(currentCell.getNumber());
        currentCell.setNumber(0);
        server.answerOnIncreaseCounterOfPlayer(name);
    }
}
