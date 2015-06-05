package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.PrivateMessageDTO;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
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
        return false;
    }
}
