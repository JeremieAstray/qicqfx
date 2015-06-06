package com.jeremie.qicqfx.server.socket;


import com.jeremie.qicqfx.server.constants.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by jeremie on 2015/6/4.
 */
public class QicqSokcet implements Runnable {

    private static Logger logger = LogManager.getLogger(QicqSokcet.class);
    public ObjectOutputStream objectOutputStream = null;
    public ObjectInputStream objectInputStream = null;
    private Socket socket;
    private DataHandler dataHandler;

    public QicqSokcet(Socket sockek, DataHandler dataHandler) {
        this.socket = sockek;
        this.dataHandler = dataHandler;
    }

    public void sendData(Object o) {
        try {
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            while (true) {
                Object o = objectInputStream.readObject();
                if (dataHandler.handleMessage(o, this))
                    break;
            }
            objectOutputStream.writeObject(null);
            Thread.sleep(200);
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                if (!socket.isClosed())
                    socket.getInputStream().close();
            } catch (IOException e) {
                logger.error(e);
            }
            try {
                logger.debug(socket.getInetAddress() + " close!");
                socket.close();
            } catch (IOException e) {
                logger.error(e);
            }

        }
    }
}
