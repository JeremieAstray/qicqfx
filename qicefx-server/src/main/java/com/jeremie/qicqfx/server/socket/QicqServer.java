package com.jeremie.qicqfx.server.socket;


import com.jeremie.qicqfx.server.constants.Constants;
import com.jeremie.qicqfx.server.constants.DataHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Jeremie on 2015/5/13.
 */
public class QicqServer {
    private DataHandler dataHandler = Constants.dataHandler;

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Constants.PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new QicqSokcet(socket,dataHandler));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}
