package com.myos.netty.threadmodule;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wu sir
 * @Date: 2020/5/21 2:55 下午
 */
public class NioModuleServer {


    public static void main(String[] args) {

        List<SocketChannel> list = new ArrayList();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(9001));
            ssc.configureBlocking(false);
            while (true){
                SocketChannel socketChannel = ssc.accept();
                if(socketChannel==null){
                    Thread.sleep(1000);
                    System.out.println("没人连接");
                }else{
                    socketChannel.configureBlocking(false);
                    list.add(socketChannel);
                    System.out.println("new socketChannel add in list");
                }
                //得到套接字，循环所有的套接字，通过套接字获取数据
                for (SocketChannel channel : list) {
                    int k = channel.read(byteBuffer);
                    System.out.println("read k = " + k);
                    if(k!=0){
                        byteBuffer.flip();
                        System.out.println("read content:" + new String(byteBuffer.array()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();

    }


}
