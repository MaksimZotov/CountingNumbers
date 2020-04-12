package controller.obligations;

public interface ClientObligations {
    void joinPlayer(String name);
    void movePlayer(String name, String direction);
    void increaseCounterOfPlayer(String name);

    // Test
    String answerOnJoinPlayer(String name);

    void answerOnMovePlayer(String name);
    void answerOnIncreaseCounterOfPlayer(String name);
    void getEventPlayerLost(String name);
}
