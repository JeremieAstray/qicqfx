package com.jeremie.qicqfx.server.socket;


import com.jeremie.qicqfx.server.constants.Constants;
import com.jeremie.qicqfx.server.constants.DataHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Jeremie on 2015/5/13.
 */
public class QicqServer {

    private static Logger logger = LogManager.getLogger(QicqServer.class);
    private DataHandler dataHandler = Constants.dataHandler;

    public void start() {
        logger.debug("开启QICQ服务，端口号：" + Constants.PORT);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Constants.PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new QicqSokcet(socket,dataHandler));
                thread.start();
            }
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                logger.debug("关闭QICQ服务");
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
