package com.jeremie.qicqfx.dto;

import java.io.Serializable;

/**
 * Created by jeremie on 2015/6/4.
 */
public class MessageDTO implements Serializable {
    protected Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}
