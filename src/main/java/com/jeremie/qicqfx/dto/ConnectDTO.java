package com.jeremie.qicqfx.dto;

/**
 * Created by jeremie on 2015/6/4.
 */
public class ConnectDTO extends MessageDTO {
    private User user;
    private boolean connected;
    private boolean end;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
