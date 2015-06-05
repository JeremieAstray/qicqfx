package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.ConnectDTO;
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
@Service("ConnectService")
public class ConnectService implements BaseService<ConnectDTO> {
    private static Logger logger = LogManager.getLogger(ConnectService.class);

    @Override
    public boolean handleMessage(ConnectDTO message, QicqSokcet qicqSokcet) {
        if (message.getUsername() == null) {
            DisconnectDTO disconnectDTO = new DisconnectDTO(true);
            disconnectDTO.setReason("信息传输错误，断开连接");
            qicqSokcet.sendData(disconnectDTO);
        } else if (Constants.onlineUsers.keySet().contains(message.getUsername())) {
            DisconnectDTO disconnectDTO = new DisconnectDTO(true);
            disconnectDTO.setReason("该用户已存在");
            qicqSokcet.sendData(disconnectDTO);
        } else {
            logger.debug("用户" + message.getUsername() + "登陆" );
            Constants.onlineUsers.put(message.getUsername(), qicqSokcet);
            ConnectDTO connectDTO = new ConnectDTO();
            connectDTO.setUsername(message.getUsername());
            qicqSokcet.sendData(connectDTO);
            OnlineUsersMessageDTO onlineUsersMessageDTO = new OnlineUsersMessageDTO();
            onlineUsersMessageDTO.setOnlineUsers((String[]) Constants.onlineUsers.keySet().toArray());
            for (QicqSokcet sokcet : Constants.onlineUsers.values())
                sokcet.sendData(onlineUsersMessageDTO);
        }
        return false;
    }
}
