package com.qww.clz.thread.created;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 16:21
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +"******"+System.currentTimeMillis());
    }
}
