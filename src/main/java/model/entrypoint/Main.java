package model.entrypoint;

import controller.server.Server;
import model.sessionmanagers.SessionManagerModel;

public class Main {
    public static void main(String[] args) { new Server(new SessionManagerModel()).main(); }
}
