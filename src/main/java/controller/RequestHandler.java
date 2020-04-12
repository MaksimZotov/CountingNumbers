package controller;

public interface RequestHandler {
    void joinPlayer(String name);
    void movePlayer(String name, String direction);
    void increaseCounterOfPlayer(String name);
}
