package com.qww.clz.other.cas;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author chenglezheng
 * @Date 2020/7/2 16:38
 */
public class LockTest {

    private static int count = 0;

    private static AtomicInteger count1 = new AtomicInteger(0);


    @Test
    public void test1() {
        for (int i = 0; i < 2; i++) {
            new Thread(
                () -> {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //每个线程让count自增100次
                    for (int j = 0; j < 100; j++) {
                        synchronized (LockTest.class) {
                            count++;
                        }
                    }
                }
            ).start();
        }
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }


    @Test
    public void test2() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //每个线程让count自增100次
                for (int j = 0; j < 100; j++) {
                    count1.incrementAndGet();
                }
            }
            ).start();
        }

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count1);
    }


}
