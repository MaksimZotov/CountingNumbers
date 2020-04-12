package controller.obligations;

public interface ServerObligations {
    void main();

    void joinPlayer(String name);
    void movePlayer(String name, String direction);
    void increaseCounterOfPlayer(String name);

    void answerOnJoinPlayer(String name);
    void answerOnMovePlayer(String name);
    void answerOnIncreaseCounterOfPlayer(String name);
    void sendEventPlayerLost(String name);
}
