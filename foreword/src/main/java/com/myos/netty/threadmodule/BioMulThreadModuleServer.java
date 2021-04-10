package com.myos.netty.threadmodule;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: wu sir
 * @Date: 2020/5/21 2:55 下午
 */
public class BioMulThreadModuleServer {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9001);
            System.out.println("bio socket start port 9001");
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("accept info " + socket.getRemoteSocketAddress());
                new Thread(new Runnable() {
                    public void run() {
                        byte[] byteRead = new byte[1024];
                        //阻塞
                        try {
                            socket.getInputStream().read(byteRead);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(socket.getRemoteSocketAddress() + " input content:" + new String(byteRead));
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
