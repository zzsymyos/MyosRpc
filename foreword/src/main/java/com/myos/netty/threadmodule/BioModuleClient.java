package com.myos.netty.threadmodule;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: wu sir
 * @Date: 2020/5/21 2:55 下午
 */
public class BioModuleClient {


    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",9000);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String text = scanner.next();
                socket.getOutputStream().write(text.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
