package com.jeremie.qicqfx.server.socket;


import com.jeremie.qicqfx.server.constants.Constants;
import com.jeremie.qicqfx.server.constants.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Jeremie on 2015/5/13.
 */
public class MyServer {
    private DataHandler dataHandler = Constants.dataHandler;

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Constants.PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, str -> {
                    for (Map.Entry<String, ServerThread> user : onlineUsers.entrySet()) {
                        ObjectOutputStream objectOutputStream = user.getValue().objectOutputStream;
                        if (objectOutputStream != null) {
                            try {
                                objectOutputStream.writeObject(str);
                                objectOutputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    return true;
                });
                Thread thread = new Thread(serverThread);
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
