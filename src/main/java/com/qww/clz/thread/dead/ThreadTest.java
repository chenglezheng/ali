package com.qww.clz.thread.dead;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 20:09
 */
public class ThreadTest {

    public static void main(String[] args) {
        Thread thread1=new Thread(new DeadLock(1),"线程1");
        Thread thread2=new Thread(new DeadLock(2),"线程2");
        thread1.start();
        thread2.start();
    }

}
