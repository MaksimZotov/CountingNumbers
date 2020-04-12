package controller.obligations;

public interface ApplicationObligations {
    void answerOnJoinPlayer(String data);
    void answerOnMovePlayer(String data);
    void answerOnIncreaseCounterOfPlayer(String data);
    void getEventPlayerLost(String data);
}
