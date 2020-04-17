package controller.obligations;

import java.net.Socket;

public interface SessionManager {
    void addSocket(Socket serverSocket);
    void removeSession(int sessionIndex);
}
