package com.jeremie.qicqfx.dto;

/**
 * Created by jeremie on 2015/6/5.
 */
public class DisconnectDTO extends MessageDTO {
    private String username;
    private String reason;

    public DisconnectDTO(boolean disconnected) {
        status = disconnected ? Status.DISCONNECTED : Status.END_SIGNAL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
