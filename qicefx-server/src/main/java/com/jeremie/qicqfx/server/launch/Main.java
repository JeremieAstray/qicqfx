package com.jeremie.qicqfx.server.launch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Jeremie on 2015/5/18.
 */
public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        //SpringContextHolder.applicationContext.getBean(Test.class);
        logger.info("programme start");
    }
}
