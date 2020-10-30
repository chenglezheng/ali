package com.qww.clz.thread.safe;

import org.junit.Test;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 19:26
 */
public class ThreadTest {


    public static void main(String[] args) {
        Ticket ticket=new Ticket();

        Thread thread=new Thread(ticket,"窗口1");
        Thread thread1=new Thread(ticket,"窗口2");
        Thread thread2=new Thread(ticket,"窗口3");

        thread1.start();
        thread.start();
        thread2.start();

    }

}
