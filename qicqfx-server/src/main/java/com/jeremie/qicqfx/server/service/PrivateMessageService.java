package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.ErrorMessageDTO;
import com.jeremie.qicqfx.dto.PrivateMessageDTO;
import com.jeremie.qicqfx.server.constants.Constants;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jeremie on 2015/6/5.
 */
@Service("PrivateMessageService")
public class PrivateMessageService  implements BaseService<PrivateMessageDTO> {
    private static Logger logger = LogManager.getLogger(PrivateMessageService.class);
    @Override
    public boolean handleMessage(PrivateMessageDTO message, QicqSokcet qicqSokcet) {
        if(StringUtils.isEmpty(message.getReceiver())||StringUtils.isEmpty(message.getSender())
                ||(message.isImage() && message.getImage()==null)
                ||(!message.isImage() && StringUtils.isEmpty(message.getMessage()))
                ||(!Constants.onlineUsers.containsKey(message.getSender()))
                ||(!Constants.onlineUsers.containsKey(message.getReceiver())))
            qicqSokcet.sendData(new ErrorMessageDTO("私信消息错误"));
        else {
            message.setCreateTime(System.currentTimeMillis());
            Constants.onlineUsers.get(message.getReceiver()).sendData(message);
        }
        return false;
    }
}
