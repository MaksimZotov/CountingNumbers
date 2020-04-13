package controller.client;

import java.io.BufferedWriter;
import java.io.IOException;

public class ClientSender extends Thread {
    private BufferedWriter out;
    private String dataFromClient;

    private boolean runWasLaunchedFromStart = true;

    public ClientSender(BufferedWriter out) {
        this.out = out;
        start();
    }

    @Override
    public void run() {
        if (runWasLaunchedFromStart) {
            runWasLaunchedFromStart = false;
            return;
        }
        try {
            out.write(dataFromClient + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToServer(String data) {
        dataFromClient = data;
        run();
    }
}
