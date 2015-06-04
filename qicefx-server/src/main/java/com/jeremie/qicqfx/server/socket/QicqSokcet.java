package com.jeremie.qicqfx.server.socket;


import com.jeremie.qicqfx.dto.MessageDTO;
import com.jeremie.qicqfx.server.constants.DataHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by jeremie on 2015/6/4.
 */
public class QicqSokcet implements Runnable {
    private Socket socket;
    private String name = null;
    public ObjectOutputStream objectOutputStream = null;
    public ObjectInputStream objectInputStream = null;
    private DataHandler dataHandler;

    public QicqSokcet(Socket sockek, DataHandler dataHandler) {
        this.socket = sockek;
        this.dataHandler = dataHandler;
    }

    public void sendData(Object o){
        try {
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            while(true) {
                Object o = objectInputStream.readObject();
                if (dataHandler.handleMessage(o, this))
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (!socket.isClosed())
                    socket.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(name + socket.getInetAddress() + " close!");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
