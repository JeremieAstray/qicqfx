package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.ErrorMessageDTO;
import com.jeremie.qicqfx.dto.GroupMessageDTO;
import com.jeremie.qicqfx.server.constants.Constants;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jeremie on 2015/6/5.
 */
@Service("GroupMessageService")
public class GroupMessageService implements BaseService<GroupMessageDTO> {
    private static Logger logger = LogManager.getLogger(GroupMessageService.class);

    @Override
    public boolean handleMessage(GroupMessageDTO message, QicqSokcet qicqSokcet) {
        if(StringUtils.isEmpty(message.getSender())
                ||(message.isImage() && message.getImage()==null)
                ||(!message.isImage() && StringUtils.isEmpty(message.getMessage()))
                ||(!Constants.onlineUsers.containsKey(message.getSender())))
            qicqSokcet.sendData(new ErrorMessageDTO("群发消息错误"));
        else
            Constants.onlineUsers.values().stream()
                    .filter(reQicqSokcet -> reQicqSokcet != qicqSokcet)
                    .forEach(reQicqSokcet -> reQicqSokcet.sendData(message));
        return false;
    }
}
