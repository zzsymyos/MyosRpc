package com.myos.netty.threadmodule;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author: wu sir
 * @Date: 2020/5/21 2:55 下午
 */
public class SelectModuleServer {


    public static void main(String[] args) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress("127.0.0.1", 9001));
            ssc.configureBlocking(false);

            Selector selector = Selector.open();
            //1、注册 channel，并且指定感兴趣的事件是 Accept
            //2、SelectionKey一共有4种值，分别代表4个事件：connect、accept、read、write
            //3、通过方法 interestOps 可以得到注册时对channel感兴趣的事件，具体获取方式为
            // interestSet & SelectionKey.OP_ACCEPT 得到的结果即是否为ACCEPT事件
            //4、通过这种方式即实现了注册，表明当前channel需要监听的是 read 事件,如果对多个事件感兴趣，
            // 那么可以使用 SelectionKey.OP_READ | SelectionKey.OP_WRITE 方式实现
            //5、注册方法还可以添加另一个参数，attach,用来附加更多的信息给channel,比如将Buffer给channel
            SelectionKey register = ssc.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer readBuff = ByteBuffer.allocate(1024);
            ByteBuffer writeBuff = ByteBuffer.allocate(128);
            writeBuff.put("received succ".getBytes());
            writeBuff.flip();

            while (true) {
                //select（）对channel注册的事件如果一个都没有好，那么阻塞住，返回值表示事件已经发生的chanel的个数;
                //selectNow（）则不阻塞，没有准备好就返回0
                int nReady = selector.select();
                if (nReady == 0) continue;
                //用来获取准备好的channel
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        System.out.println("isaccept");
                        // 创建新的连接，并且把连接注册到selector上，而且，
                        // 声明这个channel只对读操作感兴趣。
                        SocketChannel socketChannel = ssc.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    else if (key.isConnectable()) {
                        System.out.println("isconnect");
                    }
                    else if (key.isReadable()) {
                        System.out.println("isreadable");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuff.clear();
                        socketChannel.read(readBuff);

                        readBuff.flip();
                        System.out.println("received :" + new String(readBuff.array()));
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                    else if (key.isWritable()) {
                        System.out.println("iswriteable");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.write(writeBuff);
                        writeBuff.rewind();
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
