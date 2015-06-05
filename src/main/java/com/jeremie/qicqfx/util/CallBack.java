package com.jeremie.qicqfx.util;

import com.jeremie.qicqfx.dto.MessageDTO;

/**
 * Created by jeremie on 2015/5/31.
 */
public interface CallBack<T extends MessageDTO> {
    public void call(T data);
}
