package controller;

public interface ServerObligations {
    public abstract void joinPlayer(String name);
    public abstract void movePlayer(String name, String direction);
    public abstract void increaseCounterOfPlayer(String name);

    public abstract void answerOnJoinPlayer(String name);
    public abstract void answerOnMovePlayer(String name);
    public abstract void answerOnIncreaseCounterOfPlayer(String name);
    public abstract void sendEventPlayerLost(String name);
}
