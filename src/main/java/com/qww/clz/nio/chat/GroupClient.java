package com.qww.clz.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author chenglezheng
 * @Date 2020/7/24 16:34
 */
public class GroupClient {

    private final String HOST = "127.0.0.1";

    private final int PORT = 9898;

    private Selector selector = null;

    private SocketChannel socketChannel = null;

    /**
     * 构造函数初始化客户端
     */
    public GroupClient() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 读消息
     */
    public void read() {
//        while (true) {
//            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//            while (iterator.hasNext()) {
//                SelectionKey selectionKey = iterator.next();
//                if (selectionKey.isReadable()) {
//                    try {
//                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
//                        socketChannel.configureBlocking(false);
//                        ByteBuffer buffer = ByteBuffer.allocate(1024);
//                        if (socketChannel.read(buffer) > 0) {
//                            socketChannel.read(buffer);
//                            System.out.println(new String(buffer.array(), 0, buffer.limit()));
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                iterator.remove();
//            }
//
//        }

        try {

            int readChannels = selector.select();
            if(readChannels > 0) {//有可以用的通道

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();
                    if(key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove(); //删除当前的selectionKey, 防止重复操作
            } else {
                //System.out.println("没有可以用的通道...");

            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写消息
     *
     * @param msg
     */
    public void write(String msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);

    }


    public static void main(String[] args) throws Exception{
        GroupClient groupClient = new GroupClient();

        Thread thread=new Thread(()->{
            while (true) {
                groupClient.read();
                try {
                    Thread.currentThread().sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            groupClient.write(scanner.next());
        }
    }


}
