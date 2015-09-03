package com.jeremie.qicqfx.server.launch;

import com.jeremie.qicqfx.server.socket.QicqServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Jeremie on 2015/5/18.
 */
public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("programme start");
        //启动qicq服务器
        QicqServer qicqServer = new QicqServer();
        qicqServer.start();
    }
}
