package com.jeremie.qicqfx.util;

import java.util.Vector;

/**
 * ��Ϣ���С�<br>
 * Created on 2014/2/28.
 * @author Jeremie.Astray
 *
 */
public class MsgQueue<T extends Object> {

    private Vector<T> queue = null;

    /**
     * �����ࣺ��Ϣ����
     */
    public MsgQueue(){
        queue  = new Vector<T>();
    }

    public synchronized void send(T o){
        queue.addElement(o);
    }

    public synchronized T recv(){
        if(queue.size()==0)
            return null;
        T o = queue.firstElement();
        queue.removeElementAt(0);
        return o;
    }

    public synchronized boolean isEmpty(){
        return queue.isEmpty();
    }
}
