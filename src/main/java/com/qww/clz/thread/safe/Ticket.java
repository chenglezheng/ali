package com.qww.clz.thread.safe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 19:23
 */
public class Ticket implements Runnable {

    private int ticket = 100;

    private Object lock = new Object();

    private Lock lock1 = new ReentrantLock(true); //true代表为公平锁，false为非公平锁（某个线程独占）


    @Override
    public void run() {
//        //同步代码块，实现线程安全
//        synchronized (lock){
//            while (true){
//                if(ticket>0){
//                    try {
//                        Thread.sleep(10);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName()+"：售出第"+ticket--+"票！！！");
//                }
//            }
//        }

        //同步方法实现线程安全 不加static实际上是对象锁 加了是类锁
//        saleTicket();

        //可重入锁实现线程安全
        try {
            lock1.lock();
            while (true) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "：售出第" + ticket-- + "票！！！");
                }
            }
        } finally {
            lock1.unlock();
        }


    }


   /* public synchronized void saleTicket(){
        while (true){
            if(ticket>0){
                try {
                    Thread.sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"：售出第"+ticket--+"票！！！");
            }
        }
    }*/

}
