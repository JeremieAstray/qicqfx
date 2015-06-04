package com.jeremie.qicqfx.client.socket;


import com.jeremie.qicqfx.dto.MessageDTO;

import java.io.*;
import java.net.Socket;

/**
 * Created by Jeremie on 2015/5/13.
 */
public class MyClient {
    private static String name = null;

    public static void main(String[] args) {
        Socket socket = null;
        Reader reader = null;
        BufferedReader brConsle = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            socket = new Socket("127.0.0.1", 8000);
//            socket.getChannel().configureBlocking(false);
            reader = new InputStreamReader(System.in);
            brConsle = new BufferedReader(reader);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            final ObjectInputStream finalois = objectInputStream;
           /* Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        MessageDTO messageDTO = (MessageDTO) finalois.readObject();
                        System.out.println(messageDTO.getSender() + ":" + messageDTO.getMessage());
                        if ("server receive:END".equals(messageDTO.getMessage()))
                            break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            thread.start();*/
            while (true) {
                String message = brConsle.readLine();
                if (name == null)
                    name = message;
                MessageDTO messageDTO = new MessageDTO();
                /*messageDTO.setMessage(message);
                messageDTO.setSender(name);*/
                objectOutputStream.writeObject(messageDTO);
                objectOutputStream.flush();
                if ("END".equals(message)) {
                    /*try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    break;
                }
            }
           // thread.interrupt();
        } catch (IOException e) {
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
                if (brConsle != null)
                    brConsle.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
