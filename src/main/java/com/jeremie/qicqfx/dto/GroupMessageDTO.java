package com.jeremie.qicqfx.dto;

import java.io.Serializable;

/**
 * Created by jeremie on 2015/6/4.
 */
public class GroupMessageDTO implements Serializable{
    private String sender;
    private String message;
    private Long createTime;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
