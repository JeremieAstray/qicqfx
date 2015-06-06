package com.jeremie.qicqfx.server.constants;


import com.jeremie.qicqfx.dto.MessageDTO;
import com.jeremie.qicqfx.server.service.BaseService;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by jeremie on 2015/5/31.
 */
public class DataHandler {
    private static Logger logger = LogManager.getLogger(DataHandler.class);

    public boolean handleMessage(Object o, QicqSokcet qicqSokcet) {
        boolean flag = false;
        if(o instanceof MessageDTO) {
            MessageDTO messageDTO = (MessageDTO)o;
            String beanName = messageDTO.getClass().getSimpleName().replace("DTO", "Service");
            BaseService baseService = (BaseService) SpringContextHolder.applicationContext.getBean(beanName);
            flag = baseService.handleMessage(messageDTO, qicqSokcet);
        }
        return flag;
    }

    public void close(QicqSokcet qicqSokcet){
        for(Map.Entry<String,QicqSokcet> map:Constants.onlineUsers.entrySet())
            if(map.getValue()==qicqSokcet)
                Constants.onlineUsers.remove(map.getKey());
    }
}
