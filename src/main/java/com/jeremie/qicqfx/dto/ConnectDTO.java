package com.jeremie.qicqfx.dto;

/**
 * Created by jeremie on 2015/6/4.
 */
public class ConnectDTO extends MessageDTO {
    private String username;
    public ConnectDTO() {
        status = Status.CONNECTED;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
