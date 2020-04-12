package controller.obligations;

public interface ClientObligations {
    void main(String data);

    void joinPlayer(String data);
    void movePlayer(String data, String direction);
    void increaseCounterOfPlayer(String data);


    void answerOnJoinPlayer(String data);
    void answerOnMovePlayer(String data);
    void answerOnIncreaseCounterOfPlayer(String data);
    void getEventPlayerLost(String data);
}
