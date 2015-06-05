package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.OnlineUsersMessageDTO;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jeremie on 2015/6/5.
 */
@Service("OnlineUsersMessageService")
public class OnlineUsersMessageService implements BaseService<OnlineUsersMessageDTO> {
    private static Logger logger = LogManager.getLogger(OnlineUsersMessageService.class);

    @Override
    public boolean handleMessage(OnlineUsersMessageDTO message, QicqSokcet qicqSokcet) {
        return false;
    }
}
