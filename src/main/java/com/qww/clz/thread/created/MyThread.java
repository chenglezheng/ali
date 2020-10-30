package com.qww.clz.thread.created;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 16:19
 */
public class MyThread  extends Thread{

    @Override
    public void run() {
        System.out.println(MyThread.class);
        System.out.println(Thread.currentThread().getName()+"-----"+System.currentTimeMillis());
    }
}
