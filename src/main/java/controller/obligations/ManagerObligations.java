package controller.obligations;

public interface ManagerObligations {
    void joinPlayer(String name);
    void movePlayer(String name, String direction);
    void increaseCounterOfPlayer(String name);

    //Test
    String answerOnJoinPlayer();
}
