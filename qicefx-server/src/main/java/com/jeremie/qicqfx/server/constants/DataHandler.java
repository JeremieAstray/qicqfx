package com.jeremie.qicqfx.server.constants;


import com.jeremie.qicqfx.dto.MessageDTO;
import com.jeremie.qicqfx.util.CallBack;
import com.jeremie.qicqfx.util.MsgQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremie on 2015/5/31.
 */
public class DataHandler implements Runnable {

    private List<CallBack<MessageDTO>> callBackList;
    private MsgQueue<MessageDTO> msgQueue;

    public DataHandler() {
        this.msgQueue = new MsgQueue<>();
        callBackList = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!msgQueue.isEmpty()) {
                MessageDTO messageDTO = msgQueue.recv();
                for(CallBack<MessageDTO> callBack:callBackList){
                    //if(callBack.)
                    messageDTO.getStatus().getClazz();
                }
            }
        }
    }

    public void addCallback(CallBack<MessageDTO> callBack){
        callBackList.add(callBack);
    }
}
