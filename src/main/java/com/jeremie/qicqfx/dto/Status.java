package com.jeremie.qicqfx.dto;

import java.io.Serializable;

/**
 * Created by Jeremie on 2015/5/21.
 */
public enum Status implements Serializable {

    CONNECTED(1,ConnectDTO.class),
    DISCONNECTED(0,DisconnectDTO.class),
    END_SIGNAL(-1,DisconnectDTO.class),
    PRIVATE_MESSAGE(2,PrivateMessageDTO.class),
    GROUP_MESSAGE(3,GroupMessageDTO.class),
    ONLINEUSERS_MESSAGE(4,OnlineUsersMessageDTO.class),
    ERROR_MESSAGE(5,ErrorMessageDTO.class);

    Status(int statusNum, Class clazz) {
        this.statusNum = statusNum;
        this.clazz = clazz;
    }

    private int statusNum;
    private Class clazz;

    public int getStatusNum() {
        return statusNum;
    }

    public Class getClazz() {
        return clazz;
    }
}
