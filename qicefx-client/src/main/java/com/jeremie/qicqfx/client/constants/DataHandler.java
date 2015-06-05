package com.jeremie.qicqfx.client.constants;


import com.jeremie.qicqfx.util.CallBack;
import com.jeremie.qicqfx.dto.MessageDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremie on 2015/5/31.
 */
public class DataHandler {

    private List<CallBack> callBackList;

    public DataHandler() {
        callBackList = new ArrayList<>();
    }

    public boolean handleMessage(Object o) {
        String clazzName = o.getClass().getName();
        for (CallBack callBack : callBackList) {
            String type = callBack.getClass().getGenericInterfaces()[0].getTypeName();
            type = type.substring(type.indexOf('<') + 1, type.indexOf('>'));
            if (type.equals(clazzName))
                callBack.call((MessageDTO) o);
        }
        return true;
    }

    public void addCallback(CallBack callBack) {
        callBackList.add(callBack);
    }
}
