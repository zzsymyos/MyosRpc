package com.myos.netty.nullselect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: wu sir
 * @Date: 2020/6/3 3:33 下午
 */
public class TestServer {

    private static final long SLEEP_PERIOD = 5000L; // 5 seconds
    private static final int BUFFER_SIZE = 8192;
    private int port;

    public TestServer(int port) {
        this.port = port;
    }

    //0. server waits for connection
    //1. client connects and write message
    //2. server accepts and register OP_READ
    //3. server reads message and remove OP_READ from interest op set
    //4. client close the connection
    //5. server write message (without any reading.. surely OP_READ is not set)
    //6. server's select wakes up infinitely with return value 0
    // 复现步骤 https://bugs.java.com/bugdatabase/view_bug.do?bug_id=6670302
    //  server启动 client启动
    // cd /Users/xueli/PersonalProjects/dubbo/rpcframe/MyosRpc/foreword/target/classes
    // java -cp . com.myos.netty.nullselect.TestServer 7777
    // java -cp . com.myos.netty.nullselect.TestClient 10.253.101.171 7777
    public static void main(String[] args) throws Throwable {
        if (args.length < 1) {
            System.err.println("Usage : java TestServer <port>");
            System.exit(0);
        }

        new TestServer(Integer.parseInt(args[0])).start();
    }

    public void start() throws Throwable {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket server = serverChannel.socket();
        server.bind(new InetSocketAddress(port));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        SocketChannel clientChannel = null;

        System.out.println("0. SERVER STARTED TO LISTEN");
        boolean writeNow = false;

        while (true) {
            try {
                // wait for selection
                int numKeys = selector.select();

                if (numKeys == 0) {
                    System.err.println("select wakes up with zero!!!");
                }

                Iterator it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selected = (SelectionKey) it.next();
                    int ops = selected.interestOps();

                    try {
                        // process new connection
                        if ((ops & SelectionKey.OP_ACCEPT) != 0) {
                            clientChannel = serverChannel.accept();
                            clientChannel.configureBlocking(false);

                            // register channel to selector
                            clientChannel.register(selector, SelectionKey.OP_READ, null);
                            System.out.println("2. SERVER ACCEPTED AND REGISTER READ OP : client - " + clientChannel.socket().getInetAddress());
                        }

                        if ((ops & SelectionKey.OP_READ) != 0) {
                            // read client message
                            System.out.println("3. SERVER READ DATA FROM client - " + clientChannel.socket().getInetAddress());
                            readClient((SocketChannel) selected.channel(), buffer);

                            // deregister OP_READ
                            System.out.println("PREV SET : " + selected.interestOps());
                            selected.interestOps(selected.interestOps() & ~SelectionKey.OP_READ);
                            System.out.println("NEW SET : " + selected.interestOps());

                            Thread.sleep(SLEEP_PERIOD * 2);
                            new WriterThread(clientChannel).start();
                        }

                    } finally {
                        // remove from selected key set
                        it.remove();
                    }
                }
            } catch (IOException e) {
                System.err.println("IO Error : " + e.getMessage());
            }
        }
    }


    public void readClient(SocketChannel channel, ByteBuffer buffer) throws IOException {
        try {
            buffer.clear();

            int nRead = channel.read(buffer);

            if (nRead < 0) {
                channel.close();
                return;
            }

            if (buffer.position() != 0) {
                int size = buffer.position();
                buffer.flip();
                byte[] bytes = new byte[size];
                buffer.get(bytes);
                System.out.println("RECVED : " + new String(bytes));
            }
        } catch (IOException e) {
            System.err.println("IO Error : " + e.getMessage());
            channel.close();
        }
    }

    static class WriterThread extends Thread {
        private SocketChannel clientChannel;
        public WriterThread(SocketChannel clientChannel) {
            this.clientChannel = clientChannel;
        }

        public void run() {
            try {
                writeClient(clientChannel);
                System.out.println("5. SERVER WRITE DATA TO client - " + clientChannel.socket().getInetAddress());
            } catch (IOException e) {
                System.err.println("5. SERVER WRITE DATA FAILED : " + e);
            }
        }

        public void writeClient(SocketChannel channel) throws IOException {
            try {
                ByteBuffer buffer = ByteBuffer.wrap("zwxydfdssdfsd".getBytes());
                int total = buffer.limit();

                int totalWrote = 0;
                int nWrote = 0;

                while ((nWrote = channel.write(buffer)) >= 0) {
                    totalWrote += nWrote;
                    if (totalWrote == total) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("IO Error : " + e.getMessage());
                channel.close();
            }
        }


    }
}
