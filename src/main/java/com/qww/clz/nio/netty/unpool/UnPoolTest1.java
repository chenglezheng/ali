package com.qww.clz.nio.netty.unpool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author chenglezheng
 * @Date 2020/8/17 19:27
 */
public class UnPoolTest1 {


    public static void main(String[] args) {

        /**
         * 创建一个ByteBuf
         * 说明
         * 1.创建对象，该对象包含一个数组arr，是一个Byte【10】
         * 2.在netty的buffer中，不需要使用flip进行反转
         * 3.通过readerIndex和writeIndex 和capacity ,将buffer分为三个区
         * 0  --- readerIndex  已经读取区域
         * readerIndex---writeIndex 可读区域
         * writeIndex --- capacity 可写区域
         *
         */
        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }

        System.out.println("capacity="+byteBuf.capacity());


        for (int i = 0; i <byteBuf.capacity() ; i++) {
            System.out.println(byteBuf.readByte());
        }

        for (int i = 0; i <byteBuf.capacity() ; i++) {
            System.out.println(byteBuf.getByte(i));
        }

    }

}
