package com.qww.clz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author chenglezheng
 * @Date 2020/7/7 19:21
 */
public class NIOServer {


    private static Selector selector;

    private static LinkedBlockingQueue<SelectionKey> requestQueue;

    private static ExecutorService threadPool;

    private static void init() {

        ServerSocketChannel serverSocketChannel = null;

        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //将Channel设置为非阻塞 NIO就是支持非阻塞
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(9000), 100);
            //ServerSocket,就是负责去跟各个客户端连接请求的
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //就是仅仅关注这个ServerSocketChannel接收到TCP连接的请求
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestQueue = new LinkedBlockingQueue<SelectionKey>(500);
        threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            threadPool.submit(new Worker());
        }

    }


    private static void listen() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = (SelectionKey) keyIterator.next();
                    //可以认为一个SelectionKey是代表一个请求
                    keyIterator.remove();
                    handleRequest(key);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void handleRequest(SelectionKey key) throws Exception {

        //后台的线程池中的线程处理下面的代码逻辑
        SocketChannel channel = null;
        try {
            //如果说这个key是一个acceptable，也就是一个连接请求
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                //调用accept方法，就可以进行TCP三次握手了
                channel = serverSocketChannel.accept();
                //握手成功后的话就可以获取到一个TCP连接好的SocketChannel
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            }
            //仅仅关注这个READ请求，也就是人家数据发过来的请求
            else if (key.isReadable()) {
                // 如果说这个key是readable，是个发送了数据过来的话，此时需要读取客户端发送过来的数据
                channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int count = channel.read(buffer);
                //通过底层的socket读取数据，写到buffer中，position可能就会变成21之类的
                //你读取到多少个字节，此时buffer的position就会变成多少
                if (count > 0) {
                    //准备读取刚写入的数据，就是将limit设置为当前position，将position设置为0，地球mark。一般就是先写入数据，接着准备从0开始读这段数据，就可以用flip
                    //position=0,limit=21,仅仅读取buffer中，0-21这段刚刚写入进去的数据
                    buffer.flip();
                    System.out.println("服务端接收请求：" + new String(buffer.array(), 0, count));
                    channel.register(selector, SelectionKey.OP_WRITE);
                }
            } else if (key.isWritable()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put("客户端，我已经收到你发过来的消息了".getBytes());
                buffer.flip();
                channel = (SocketChannel) key.channel();
                channel.write(buffer);
                channel.register(selector, SelectionKey.OP_READ);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    static class Worker implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    SelectionKey key = requestQueue.take();
                    handleRequest(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleRequest(SelectionKey key) throws IOException, ClosedChannelException {
            //假设想象一下，后台有个线程获取到了请求
            //下面的代码，都是在后台线程池的工作线程里在处理和执行

            SocketChannel channel = null;

            try {
                //如果说这个key是acceptable,是个连接请求的话
                if (key.isAcceptable()) {
                    System.out.println("[" + Thread.currentThread().getName() + "]接收连接请求");
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    //调用accept方法 和客户端进行三次护手
                    channel = serverSocketChannel.accept();
                    System.out.println("[" + Thread.currentThread().getName() + "]建立连接时获取到的channel=" + channel);
                    //如果三次握手成功了之后，就可以获取到一个建立好TCP连接的SocketChannel
                    //这个SocketChannel大概可以理解为，底层有一个Socket，是跟客户端进行连接的
                    //你的SocketChannel就是联通到那个Socket上去，负责进行网络数据的读写
                    //设置为非阻塞的
                    channel.configureBlocking(false);
                    //关注的是Reade请求
                    channel.register(selector, SelectionKey.OP_READ);
                }
                //如果说这个key是readable,是个发送了数据过来的话，此时需要读取客户端发过来的数据
                else if (key.isReadable()) {
                    channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int count = channel.read(buffer);
                    //通过底层的socket读取数据，写入buffer中，position可能就会变成21之类的
                    //你读取到了多少个字节，此时的buffer的position就会变成多少
                    System.out.println("[" + Thread.currentThread().getName() + "]接到请求");
                    if (count > 0) {
                        buffer.flip(); //position=0，limit=21,仅仅读取buffer中，0~21这段刚刚写入进去的数据
                        System.out.println("服务端接收请求：" + new String(buffer.array(), 0, count));
                        channel.register(selector, SelectionKey.OP_WRITE);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put("收到消息".getBytes());
                    buffer.flip();
                    channel = (SocketChannel) key.channel();
                    channel.write(buffer);
                    channel.register(selector, SelectionKey.OP_READ);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (channel != null) {
                    channel.close();
                }
            }
        }


    }

    public static void main(String[] args) {
        init();
        listen();
    }

}
