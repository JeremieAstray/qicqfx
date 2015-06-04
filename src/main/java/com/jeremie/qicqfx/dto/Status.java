package com.jeremie.qicqfx.dto;

/**
 * Created by Jeremie on 2015/5/21.
 */
public enum Status {

    CONNECTED(1,ConnectDTO.class),
    DISCONNECTED(0,ConnectDTO.class),
    END_SIGNAL(-1,ConnectDTO.class),
    PRIVATE_MESSAGE(2,PrivateMessageDTO.class),
    GROUP_MESSAGE(3,GroupMessageDTO.class);


    private Status(int statusNum,Class clazz) {
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
