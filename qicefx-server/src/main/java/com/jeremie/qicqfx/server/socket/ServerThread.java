package com.jeremie.qicqfx.server.socket;


import com.jeremie.qicqfx.dto.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by jeremie on 2015/6/4.
 */
public class ServerThread implements Runnable {
    private Socket socket;
    private String name = null;
    public ObjectOutputStream objectOutputStream = null;
    public ObjectInputStream objectInputStream = null;

    public ServerThread(Socket sockek) {
        this.socket = sockek;
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            boolean firstTime = true;
            while (true) {

                if (firstTime) {
                    firstTime = false;
                    MessageDTO messageDTO = null;
                    Object o = objectInputStream.readObject();
                    if(o instanceof MessageDTO){
                        messageDTO = (MessageDTO)o;
                    }else {
                        firstTime = true;
                        continue;
                    }
                    /*System.out.println("name=" + messageDTO.getSender());
                    name = messageDTO.getSender();*/
                    continue;
                }

                MessageDTO messageDTO = null;
                Object o = objectInputStream.readObject();
                if(o instanceof MessageDTO){
                    messageDTO = (MessageDTO)o;
                }
                String str = "recevice messageDTO fail!";
                /*if(messageDTO != null) {
                    str = "server receive:" + messageDTO.getMessage();
                    System.out.println(name + ": " + messageDTO.getMessage());
                }else
                    System.out.println(name + ": " + str);
                MessageDTO messageDTOServer = new MessageDTO();
                messageDTOServer.setCreateTime(System.currentTimeMillis());
                messageDTOServer.setReceiver(name);
                messageDTOServer.setSender("server");
                messageDTOServer.setMessage(str);
                objectOutputStream.writeObject(messageDTO);
                objectOutputStream.flush();
                if ("server receive:END".equals(str) || "null".equals(str)) break;
                callback.call(messageDTO);*/
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (!socket.isClosed())
                    socket.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(name + socket.getInetAddress() + " close!");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
