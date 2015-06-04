package com.jeremie.qicqfx.server.constants;


import com.jeremie.qicqfx.dto.MessageDTO;
import com.jeremie.qicqfx.server.socket.QicqSokcet;
import com.jeremie.qicqfx.util.CallBack;
import com.jeremie.qicqfx.util.MsgQueue;
import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremie on 2015/5/31.
 */
public class DataHandler{

    private List<CallBack<MessageDTO>> callBackList;

    public DataHandler() {
        callBackList = new ArrayList<>();
    }

    public boolean handleMessage(Object o,QicqSokcet qicqSokcet){

        return true;
    }

    public void sendMessage(MessageDTO messageDTO){
        //Constants.onlineUsers
    }

    public void addCallback(CallBack<MessageDTO> callBack){
        callBackList.add(callBack);
    }
}
