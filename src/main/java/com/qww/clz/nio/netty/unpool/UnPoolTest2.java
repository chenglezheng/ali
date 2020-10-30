package com.qww.clz.nio.netty.unpool;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @Author chenglezheng
 * @Date 2020/8/18 19:51
 */
public class UnPoolTest2 {


    public static void main(String[] args) {
        //创建ByteBuf
        ByteBuf buf=Unpooled.copiedBuffer("Hello",Charset.forName("utf-8"));

        if(buf.hasArray()){
            byte[] content=buf.array();
            //将content转成字符串
            System.out.println(new String(content,Charset.forName("utf-8")));
            System.out.println("byteBuf="+buf);
            System.out.println(buf.arrayOffset());
            System.out.println(buf.readerIndex());
            System.out.println(buf.writerIndex());
            System.out.println("capacity"+buf.capacity());
            System.out.println(buf.getByte(0));
            int len=buf.readableBytes();  //可读字节数
            System.out.println("len=" + len);
            //使用for循环取出各个字节
            for (int i = 0; i <len ; i++) {
                System.out.println((char)buf.getByte(i));
            }
            //按照某个范围读取
            System.out.println(buf.getCharSequence(0,4,Charset.forName("utf-8")));
            System.out.println(buf.getCharSequence(0,len,Charset.forName("utf-8")));
        }
    }


}
