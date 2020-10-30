package com.qww.clz.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author chenglezheng
 * @Date 2020/7/7 16:13
 */
public class NIOClient {


    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            Worker worker=new Worker();
            worker.start();
        }
    }


    static class Worker extends Thread {

        @Override
        public void run() {
            SocketChannel socketChannel = null;
            Selector selector = null;
            try {

                //SocketChannel,一看底层就是封装了一个Socket
                socketChannel = SocketChannel.open();  //SocketChannel是连接底层的Socket网络
                //数据通道就是负责基于网络读写数据的
                socketChannel.configureBlocking(false);
                socketChannel.connect(new InetSocketAddress("localhost", 9000));
                //后台一定是tcp三次握手建立网络连接
                selector = Selector.open();
                //监听Connect这个行为
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
                while (true) {
                    // selector多路复用机制的实现 循环去遍历各个注册的Channel
                    selector.select();
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey selectionKey = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        //如果发现返回的时候一个可连接的消息 走到下面去接受数据
                        if (selectionKey.isConnectable()) {
                            socketChannel = (SocketChannel) selectionKey.channel();
                            if (socketChannel.isConnectionPending()) {
                                socketChannel.finishConnect();
                                //接下来对这个SocketChannel感兴趣的就是人家server给你发送过来的数据了
                                //READ事件，就是可以读数据的事件
                                //一旦建立成功了以后,此事就可以给server发送一个请求了
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                buffer.put("服务器，我要连接你了！".getBytes());
                                buffer.flip();
                                socketChannel.write(buffer);
                            }

                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            // 这里的话就时候名服务器端返回了一条数据可以读了
                            socketChannel = (SocketChannel) selectionKey.channel();
                            //构建一个缓冲区
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            //把数据写入到buffer，position推进到读取的字节数数字
                            int len = socketChannel.read(buffer);
                            if (len > 0) {
                                System.out.println("[" + Thread.currentThread().getName() + "]收到响应：" +
                                        new String(buffer.array(), 0, len));
                                Thread.sleep(5000);
                                socketChannel.register(selector, SelectionKey.OP_WRITE);
                            }
                        }else if(selectionKey.isWritable()){
                            ByteBuffer buffer=ByteBuffer.allocate(1024);
                            buffer.put("连接成功，数据写入中！".getBytes());
                            buffer.flip();
                            socketChannel=(SocketChannel) selectionKey.channel();
                            socketChannel.write(buffer);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (socketChannel!=null){
                    try {
                        socketChannel.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(selector!=null){
                    try {
                        selector.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
