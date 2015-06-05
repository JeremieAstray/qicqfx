package com.jeremie.qicqfx.dto;

import java.util.List;

/**
 * Created by jeremie on 2015/6/5.
 */
public class OnlineUsersMessageDTO extends MessageDTO{

    public OnlineUsersMessageDTO() {
        status = Status.ONLINEUSERS_MESSAGE;
    }

    public List<String> onlineUsers;

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}
