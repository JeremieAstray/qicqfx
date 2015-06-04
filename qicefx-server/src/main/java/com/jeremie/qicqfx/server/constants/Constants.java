package com.jeremie.qicqfx.server.constants;

import com.jeremie.qicqfx.server.socket.ServerThread;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeremie on 2015/6/4.
 */
public class Constants {
    public static Map<String, ServerThread> onlineUsers = new HashMap<>();
    public static int PORT = 8000;
    public static DataHandler dataHandler = new DataHandler();
}
