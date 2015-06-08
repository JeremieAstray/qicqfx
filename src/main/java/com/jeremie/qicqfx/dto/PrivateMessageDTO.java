package com.jeremie.qicqfx.dto;

import java.nio.ByteBuffer;

/**
 * Created by Jeremie on 2015/5/14.
 */
public class PrivateMessageDTO extends MessageDTO{
    private String sender;
    private String receiver;
    private String message;
    private Long createTime;
    private boolean isImage;
    private byte[] image;

    public PrivateMessageDTO(){
        status = Status.PRIVATE_MESSAGE;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
