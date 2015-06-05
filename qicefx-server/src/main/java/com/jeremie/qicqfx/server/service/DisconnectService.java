package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.DisconnectDTO;
import com.jeremie.qicqfx.dto.OnlineUsersMessageDTO;
import com.jeremie.qicqfx.server.constants.Constants;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jeremie on 2015/6/5.
 */
@Service("DisconnectService")
public class DisconnectService implements BaseService<DisconnectDTO>  {
    private static Logger logger = LogManager.getLogger(DisconnectService.class);

    @Override
    public boolean handleMessage(DisconnectDTO message, QicqSokcet qicqSokcet) {
        Constants.onlineUsers.remove(message.getUsername());
        OnlineUsersMessageDTO onlineUsersMessageDTO = new OnlineUsersMessageDTO();
        onlineUsersMessageDTO.setOnlineUsers((String[]) Constants.onlineUsers.keySet().toArray());
        for (QicqSokcet sokcet : Constants.onlineUsers.values())
            sokcet.sendData(onlineUsersMessageDTO);
        return true;
    }
}