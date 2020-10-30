package com.qww.clz.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author chenglezheng
 * @Date 2020/7/24 15:53
 */
public class GroupChatServer {

    private ServerSocketChannel serverSocketChannel = null;

    private Selector selector = null;

    private final int PORT = 9898;

    /**
     * 构造函数内初始化服务器端
     */
    public GroupChatServer() {
        try {
            System.out.println("服务器端启动ing...");
            Thread.sleep(1000);
            //获得一个服务端通道
            serverSocketChannel = ServerSocketChannel.open();

            //设置监听的端口号
            serverSocketChannel.bind(new InetSocketAddress(PORT));

            //设置通道为非阻塞
            serverSocketChannel.configureBlocking(false);

            //打开选择器
            selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("启动完成，等待客户端连接中...");
            /*while (true) {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> iterator1 = selector.selectedKeys().iterator();
                    while (iterator1.hasNext()) {
                        SelectionKey selectionKey = iterator1.next();
                        if (selectionKey.isAcceptable()) {
                            listener(selectionKey);
                        } else if (selectionKey.isReadable()) {
                            read(selectionKey);
                        }
                        iterator1.remove();
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听
     *
//     * @param selectionKey
     */
    public void listener(/*SelectionKey selectionKey*/) {
        /*try {
            //获取通道
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel=serverSocketChannel.accept();
            //设置通道为非阻塞
            socketChannel.configureBlocking(false);
            //将新的通道注册到selector,并监听读事件
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println(socketChannel.getRemoteAddress()+"上线了！");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {

            //循环处理
            while (true) {

                int count = selector.select();
                if(count > 0) {//有事件处理

                    //遍历得到selectionKey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectionkey
                        SelectionKey key = iterator.next();

                        //监听到accept
                        if(key.isAcceptable()) {
                            SocketChannel sc = serverSocketChannel.accept();
                            sc.configureBlocking(false);
                            //将该 sc 注册到seletor
                            sc.register(selector, SelectionKey.OP_READ);

                            //提示
                            System.out.println(sc.getRemoteAddress() + " 上线 ");

                        }
                        if(key.isReadable()) { //通道发送read事件，即通道是可读的状态
                            //处理读 (专门写方法..)

                            read(key);

                        }
                        //当前的key 删除，防止重复处理
                        iterator.remove();
                    }

                } else {
                    System.out.println("等待....");
                }
            }

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            //发生异常处理....

        }
    }

    /**
     * 读消息
     *
     * @param selectionKey
     */
    public void read(SelectionKey selectionKey) {

        //获取客户端通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        try {
            //声明缓存区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //从缓存区中读出数据
            int count = socketChannel.read(buffer);
            if (count > 0) {
                String msg = new String(buffer.array(), 0, buffer.limit());
                msg=socketChannel.getRemoteAddress()+"说:"+msg;
                System.out.println("From "+msg);
                transformerMessage(socketChannel,msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "已下线");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 转发消息
     * @param socketChannel
     * @param msg
     */
    public void transformerMessage(SocketChannel socketChannel,String msg) throws IOException{
//        //获得所有已注册的通道
//        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//        //将消息循环转发除自己以外所有其它通道
//        while (iterator.hasNext()){
//            SocketChannel socketChannel1=(SocketChannel) iterator.next().channel();
//            if(socketChannel1!=socketChannel){
//                ByteBuffer buffer=ByteBuffer.wrap((socketChannel1.getLocalAddress()+"\n 说:"+msg).getBytes());
//                socketChannel1.write(buffer);
//            }
//        }


        System.out.println("服务器转发消息中...");
        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
        for(SelectionKey key: selector.keys()) {

            //通过 key  取出对应的 SocketChannel
            Channel targetChannel = key.channel();

            //排除自己
            if(targetChannel instanceof  SocketChannel && targetChannel != socketChannel) {

                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }
        System.out.println("消息转发完毕！");

    }


    public static void main(String[] args) {
        GroupChatServer groupChatServer=new GroupChatServer();
        groupChatServer.listener();
    }

}
