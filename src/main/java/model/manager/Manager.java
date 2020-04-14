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

    private void deletePlayer(String name) {
        for (Player item : players)
            if (item.getName().equals(name)) {
                players.remove(item);
                return;
            }
    }

    public void playerLost(String name) { }

    private void initPlayer(Player player, Cell cell, int counter) {
        player.initPlayer(cell, counter);
    }

    public void joinPlayer(String name) {
        if (players.size() == 0)
            createField(3, 8);
        players.add(new Player(this, name));
        if (players.size() == 1) {
            initPlayer(players.get(0), field.getCell(1, 0), 10);
            field.getCell(1, 0).setPlayerGreenThere(true);
        }
        if (players.size() == 2) {
            initPlayer(players.get(1), field.getCell(1, 7), 10);
            field.getCell(1, 7).setPlayerBlueThere(true);
        }
    }

    private String answerOnJoinPlayer() {
        String string = "";
        for (Player item : players)
            string += item.getName();
        return string + " Test";
    }

    public void movePlayer(String direction, String name) {
        if (players.size() == 2) {
            int f = 0;
        }
        Player player = findPlayer(name);
        Cell currentCell = player.getCell();
        Cell nextCell;
        switch (direction) {
            case "up": nextCell = field.getCell(currentCell.getY() - 1, currentCell.getX()); break;
            case "down": nextCell = field.getCell(currentCell.getY() + 1, currentCell.getX()); break;
            case "left": nextCell = field.getCell(currentCell.getY(), currentCell.getX() - 1); break;
            case "right": nextCell = field.getCell(currentCell.getY(), currentCell.getX() + 1); break;
            default: throw new IllegalArgumentException("Incorrect direction");
        }
        player.decreaseCounter(currentCell.getNumber());
        player.setCell(nextCell);

        currentCell.setPlayerBlueThere(false);
        currentCell.setPlayerGreenThere(false);
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(name)) {
                if (i % 2 == 0) nextCell.setPlayerGreenThere(true);
                else nextCell.setPlayerBlueThere(true);
                break;
            }
        }
    }

    public void increaseCounterOfPlayer(String name) {
        Player player = findPlayer(name);
        Cell currentCell = player.getCell();
        player.increaseCounter(currentCell.getNumber());
        currentCell.setNumber(0);
    }

    @Override
    public void handleDataFromClient(Object data) {
        String[] dataString = ((String) data).split(" ");
        switch (dataString[0]) {
            case "join" : {
                joinPlayer(dataString[1]);
            } break;
            case "move" : {
                movePlayer(dataString[1], dataString[2]);
            } break;
            case "increase" : {
                increaseCounterOfPlayer(dataString[1]);
            } break;
            case "exit" : {
                deletePlayer(dataString[1]);
            }
        }
        sendDataToClient(field);
    }

    @Override
    public void sendDataToClient(Object data) {
        for (ClientHandler item : server.getClientHandlers())
            item.sendDataToClient(data);
    }
}
