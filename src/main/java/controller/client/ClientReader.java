package controller.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReader extends Thread {
    private Client client;
    private BufferedReader in;

    public ClientReader(Client client) {
        this.client = client;
        in = client.getIn();
        start();
    }

    @Override
    public void run() {
        String data;
        try {
            while (true) {
                data = in.readLine();
                client.handleDataFromServer(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
