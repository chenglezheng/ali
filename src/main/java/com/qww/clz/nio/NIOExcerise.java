package com.qww.clz.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author chenglezheng
 * @Date 2020/7/20 15:02
 */
public class NIOExcerise {



    @Test
    public void test1(){


        String str="love";

        ByteBuffer buffer=ByteBuffer.allocate(1024);

        System.out.println("********************init**********************");
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("limit:"+buffer.limit());
        System.out.println("position:"+buffer.position());


        buffer.put(str.getBytes());
        System.out.println("********************put**********************");
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("limit:"+buffer.limit());
        System.out.println("position:"+buffer.position());


        buffer.flip();
        System.out.println("********************flip**********************");
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("limit:"+buffer.limit());
        System.out.println("position:"+buffer.position());

        byte[] bytes=new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println(new String(bytes,0,bytes.length));
        System.out.println("********************get**********************");
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("limit:"+buffer.limit());
        System.out.println("position:"+buffer.position());

        //可重复读
        buffer.rewind();
        System.out.println("********************rewind**********************");
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("limit:"+buffer.limit());
        System.out.println("position:"+buffer.position());

        //clear：清空缓冲区。但是缓冲区的数据依然存在，但是处于“被遗忘”状态
        buffer.clear();
        System.out.println("********************clear**********************");
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("limit:"+buffer.limit());
        System.out.println("position:"+buffer.position());
        System.out.println((char) buffer.get());


    }

    @Test
    public void test2(){
        String str="I fall in love with you";
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        byte[] bytes=new byte[buffer.limit()];
        buffer.get(bytes,0,2);
        System.out.println(new String (bytes,0,2));
        System.out.println(buffer.position());
        // mark() 标记此时position的位置
        buffer.mark();
        buffer.get(bytes,2,2);
        System.out.println(new String (bytes,2,2));
        System.out.println(buffer.position());
        buffer.reset();
        System.out.println(buffer.position());
        if (buffer.hasRemaining()){
            System.out.println("缓冲区中还剩余可读字节数："+buffer.remaining());
        }


    }

    @Test
    public void test3(){
        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());
        buffer=ByteBuffer.allocate(1024);
        System.out.println(buffer.isDirect());

    }


    @Test
    public void test4() throws Exception{
        long start=System.currentTimeMillis();
        FileInputStream inputStream=new FileInputStream("D:\\TencentVideo10.35.1044.0.exe");
        FileOutputStream outputStream=new FileOutputStream("D:\\TencentVideo10.35.1044111.0.exe");

        FileChannel inputChannel=inputStream.getChannel();
        FileChannel outputChannel=outputStream.getChannel();


        ByteBuffer buffer=ByteBuffer.allocate(1024);

        while (inputChannel.read(buffer)!=-1){
            buffer.flip(); //切换读数据模式
            outputChannel.write(buffer);
            buffer.clear();
        }
        inputChannel.close();
        outputChannel.close();
        inputStream.close();
        outputStream.close();
        long end=System.currentTimeMillis();
        System.out.println("耗费时间"+(end-start)+"ms");
    }


    @Test
    public void test5() throws Exception{
        long start=System.currentTimeMillis();
        FileChannel inChannel=FileChannel.open(Paths.get("D:\\TencentVideo10.35.1044.0.exe"),StandardOpenOption.READ);
        FileChannel outChannel=FileChannel.open(Paths.get("D:\\TencentVideo10.35.10441111.0.exe"),StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        MappedByteBuffer inMapperBuf=inChannel.map(FileChannel.MapMode.READ_ONLY,0,inChannel.size());
        MappedByteBuffer outMapperBuf=outChannel.map(FileChannel.MapMode.READ_WRITE,0,inChannel.size());

        byte[] bytes=new byte[inMapperBuf.limit()];
        inMapperBuf.get(bytes);
        outMapperBuf.put(bytes);

        inChannel.close();
        outChannel.close();
        long end=System.currentTimeMillis();
        System.out.println("耗费时间"+(end-start)+"ms");

    }


    @Test
    public void test6() throws Exception{
        FileChannel inChannel=FileChannel.open(Paths.get("D:\\TencentVideo10.35.1044.0.exe"),StandardOpenOption.READ);
        FileChannel outChannel=FileChannel.open(Paths.get("D:\\TencentVideo10.35.104411111111.0.exe"),StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        inChannel.transferTo(0,inChannel.size(),outChannel);
    }



    @Test
    public void client() throws Exception{
        //申明一个套接字通道
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //缓冲区
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        //往缓冲区放数据
        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        socketChannel.close();
        System.out.println("客户端断开连接");
    }



    @Test
    public void server() throws Exception{
        System.out.println("服务端已开启");
        //获取服务端套接字通道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9898));
        //获取选择器
        Selector selector=Selector.open();
        //注册
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        //轮询
        while (selector.select() >0){
            //获取SelectionKey迭代器
            Iterator<SelectionKey> selectionKeys=selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()){
                SelectionKey selectionKey=selectionKeys.next();
                if(selectionKey.isAcceptable()){
                    System.out.println("一个客户端已连接");
                    //获取socketChannel通道
                    SocketChannel socketChannel=serverSocketChannel.accept();
                    //设置非阻塞
                    socketChannel.configureBlocking(false);
                    //注册
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    //获取SocketChannel通道
                    SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
                    //读取数据
                    ByteBuffer buffer=ByteBuffer.allocate(1024);
                    int len=0;
                    while ((len=socketChannel.read(buffer))>0){
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }
                }
                selectionKeys.remove();
            }

        }

    }




}
