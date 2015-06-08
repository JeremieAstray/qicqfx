package com.jeremie.qicqfx.client.socket;


import com.jeremie.qicqfx.client.constants.Config;
import com.jeremie.qicqfx.client.constants.DataHandler;
import com.jeremie.qicqfx.util.EndSignal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
    private BlockingQueue<Object> sendingQueue = new ArrayBlockingQueue(50);

    @Override
    public void run() {
        try {
            socket = new Socket(Config.serverIp, 8000);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Thread sendingThread = new Thread(() -> {
                while(true){
                    try {
                        Object o = sendingQueue.take();
                        if(o instanceof EndSignal)
                            break;
                        objectOutputStream.writeObject(o);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sendingThread.start();
            while (true) {
                Object o = objectInputStream.readObject();
                if(o == null) {
                    sendingQueue.put(new EndSignal());
                    break;
                }
                dataHandler.handleMessage(o);
            }
        } catch (EOFException e) {
            logger.debug("socket连接结束");
        } catch(IOException | ClassNotFoundException | InterruptedException e) {
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
            sendingQueue.put(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
