package com.jeremie.qicqfx.server.service;

import com.jeremie.qicqfx.dto.MessageDTO;
import com.jeremie.qicqfx.server.socket.QicqSokcet;

import java.io.Serializable;

/**
 * Created by jeremie on 2015/6/5.
 */
public interface BaseService<T extends MessageDTO> extends Serializable {
    boolean handleMessage(T message, QicqSokcet qicqSokcet);
}
