package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.GroupMessageDTO;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
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
        return false;
    }
}
