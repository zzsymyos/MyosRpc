package com.myos.netty.threadmodule;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: wu sir
 * @Date: 2020/5/21 2:55 下午
 */
public class BioModuleServer {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                Socket socket = serverSocket.accept();
                byte[] byteRead = new byte[1024];
                //阻塞
                socket.getInputStream().read(byteRead);
                System.out.println(new String(byteRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
