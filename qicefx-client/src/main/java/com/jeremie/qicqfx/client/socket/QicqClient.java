package com.jeremie.qicqfx.client.socket;


import com.jeremie.qicqfx.client.constants.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Jeremie on 2015/5/13.
 */
public class QicqClient implements Runnable {

    private static Logger logger = LogManager.getLogger(QicqClient.class);
    private DataHandler dataHandler;
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    public QicqClient(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 8000);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                Object o = objectInputStream.readObject();
                if(o == null)
                    break;
                dataHandler.handleMessage(o);
            }
        } catch (EOFException e) {
            logger.debug("socket连接结束");
        } catch(IOException | ClassNotFoundException e) {
            logger.error(e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
            try {
                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                logger.error(e);
            }
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public void sendData(Object o) {
        try {
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
