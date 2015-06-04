package com.jeremie.qicqfx.dto;

import java.io.Serializable;

/**
 * Created by jeremie on 2015/6/4.
 */
public class MessageDTO implements Serializable {
    private PrivateMessageDTO privateMessageDTO;
    private ConnectDTO connectDTO;
    private Status status;

    public PrivateMessageDTO getPrivateMessageDTO() {
        return privateMessageDTO;
    }

    public void setPrivateMessageDTO(PrivateMessageDTO privateMessageDTO) {
        this.privateMessageDTO = privateMessageDTO;
    }

    public ConnectDTO getConnectDTO() {
        return connectDTO;
    }

    public void setConnectDTO(ConnectDTO connectDTO) {
        this.connectDTO = connectDTO;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
