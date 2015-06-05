package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.ConnectDTO;
import com.jeremie.qicqfx.dto.DisconnectDTO;
import com.jeremie.qicqfx.dto.ErrorMessageDTO;
import com.jeremie.qicqfx.dto.OnlineUsersMessageDTO;
import com.jeremie.qicqfx.server.constants.Constants;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by jeremie on 2015/6/5.
 */
@Service("ConnectService")
public class ConnectService implements BaseService<ConnectDTO> {
    private static Logger logger = LogManager.getLogger(ConnectService.class);

    @Override
    public boolean handleMessage(ConnectDTO message, QicqSokcet qicqSokcet) {
        if (message.getUsername() == null) {
            qicqSokcet.sendData(new ErrorMessageDTO("信息传输错误"));
        } else if (Constants.onlineUsers.keySet().contains(message.getUsername())) {
            qicqSokcet.sendData(new ErrorMessageDTO("该用户已存在"));
        } else {
            logger.debug("用户" + message.getUsername() + "登陆" );
            Constants.onlineUsers.put(message.getUsername(), qicqSokcet);
            ConnectDTO connectDTO = new ConnectDTO();
            connectDTO.setUsername(message.getUsername());
            qicqSokcet.sendData(connectDTO);
            OnlineUsersMessageDTO onlineUsersMessageDTO = new OnlineUsersMessageDTO();
            onlineUsersMessageDTO.setOnlineUsers(Constants.onlineUsers.keySet().stream().collect(Collectors.toList()));
            Constants.onlineUsers.values().stream().forEach(reQicqSokcet -> reQicqSokcet.sendData(onlineUsersMessageDTO));
        }
        return false;
    }
}
