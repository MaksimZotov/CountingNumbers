package controller.obligations;

public interface ServerObligations {
    void main();

    void joinPlayer(String data);
    void movePlayer(String data, String direction);
    void increaseCounterOfPlayer(String data);

    void answerOnJoinPlayer(String data);
    void answerOnMovePlayer(String data);
    void answerOnIncreaseCounterOfPlayer(String data);
    void sendEventPlayerLost(String data);
}
