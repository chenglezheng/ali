package com.qww.clz.thread.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author chenglezheng
 * @Date 2020/9/7 10:42
 */
public class Test {

    //volatile不适合复合操作，它只保证了可见性，不能保证原子性

    public volatile  int inc =0;

    public AtomicInteger atomicInteger=new AtomicInteger();

    public void increase(){
        //inc++不是原子操作
        inc++;
    }

    public void increaseAtomic(){
        atomicInteger.getAndIncrement();
    }

    public static void main(String[] args) {
        Test test=new Test();

        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    test.increase();
                    test.increaseAtomic();
                }
            }).start();
        }

        while (Thread.activeCount()>2)
        Thread.yield();
        System.out.println("int:"+test.inc);
        System.out.println("AtomicInteger:"+test.atomicInteger);

    }



}
