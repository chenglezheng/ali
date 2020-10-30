package com.qww.clz.nio;

import org.junit.Test;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author chenglezheng
 * @Date 2020/6/29 9:44
 */
public class NioTest {


    @Test
    public void testNIODemo() throws Exception{
        //构造一个传统的文件输出流
        FileOutputStream out=new FileOutputStream("D:\\test.txt");
        //通过文件输出流获取对应的FileChannel，以NIO的方式写文件
        FileChannel channel=out.getChannel();
        //将数据写入到Buffer中
        ByteBuffer buffer=ByteBuffer.wrap("Hello World!".getBytes());
        //通过FileChannel管道将Buffer中的数据写到输出流中，持久化到磁盘中
        channel.write(buffer);
        channel.close();
        out.close();
    }


}
