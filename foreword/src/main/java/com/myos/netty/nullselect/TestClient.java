package com.myos.netty.nullselect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author: wu sir
 * @Date: 2020/6/3 3:34 下午
 */
public class TestClient {
    private static final long SLEEP_PERIOD = 5000L; // 5 seconds
    private String host;
    private int port;

    public TestClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public static void main(String[] args) throws Throwable {
        if (args.length < 2 || args[0].equals("127.0.0.1") || args[0].equals("localhost")) {
            System.err.println("Usage : java TestClient <host name> <port> (host name should not be localhost)");
            System.exit(0);
        }

        new TestClient(args[0], Integer.parseInt(args[1])).start();
    }

    public void start() throws Throwable {
        Socket socket = new Socket(host, port);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()),
                true /* auto flush */);

        out.println("abcdef");

        System.out.println("1. CLIENT CONNECTED AND WROTE MESSAGE");

        Thread.sleep(SLEEP_PERIOD);

//         socket.shutdownOutput();
        socket.close();

        System.out.println("4. CLIENT SHUTDOWN OUTPUT");

        Thread.sleep(SLEEP_PERIOD * 3);
    }
}
