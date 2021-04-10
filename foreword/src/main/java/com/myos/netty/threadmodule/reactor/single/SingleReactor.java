package com.myos.netty.threadmodule.reactor.single;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: wu sir
 * @Date: 2020/6/2 11:21 上午
 */
public class SingleReactor implements Runnable{

    ServerSocketChannel serverSocketChannel;
    Selector selector;

    public SingleReactor(int port) {
        try {
            //打开tcp channel通道
            serverSocketChannel = ServerSocketChannel.open();
            // 打开一个多路io复用管理器
            selector = Selector.open();
            // 绑定监听本地ip端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            // 设置非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 注册连接事件
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new Acceptor(selector, serverSocketChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                //监听 连接和读事件 此方法会阻塞直到有io准备好
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    dispatcher(selectionKey);
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatcher(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        runnable.run();
    }

}
