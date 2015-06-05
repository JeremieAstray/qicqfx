package com.jeremie.qicqfx.server.launch;

import com.jeremie.qicqfx.dto.ConnectDTO;
import com.jeremie.qicqfx.server.constants.SpringContextHolder;
import com.jeremie.qicqfx.server.service.BaseService;
import com.jeremie.qicqfx.server.socket.QicqServer;
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
        /*ConnectDTO connectDTO = new ConnectDTO();
        String beanName =connectDTO.getClass().getSimpleName().replace("DTO","Service");
        BaseService baseService = (BaseService) SpringContextHolder.applicationContext.getBean(beanName);
        baseService.handleMessage(connectDTO,null);*/
        //¿ªÆôqicq·þÎñ
        QicqServer qicqServer = new QicqServer();
        qicqServer.start();
    }
}
