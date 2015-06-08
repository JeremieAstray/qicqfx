package com.jeremie.qicqfx.dto;

import java.nio.ByteBuffer;

/**
 * Created by jeremie on 2015/6/4.
 */
public class GroupMessageDTO extends MessageDTO {
    private String sender;
    private String message;
    private Long createTime;
    private boolean isImage;
    private byte[] image;

    public GroupMessageDTO() {
        status = Status.GROUP_MESSAGE;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

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
