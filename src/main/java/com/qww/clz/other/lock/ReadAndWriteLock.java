package com.qww.clz.other.lock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache {

    private volatile Map<String, String> map = new HashMap<>();

    private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();


    public void put() {
        lock.writeLock().lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName() + " 正在写入");
            map.put(Thread.currentThread().getName(), Thread.currentThread().getName());
            System.out.println("线程" + Thread.currentThread().getName() + " 写入成功");
        }finally {
            lock.writeLock().unlock();
        }
    }

    public void get() {
        lock.readLock().lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName() + " 正在读取");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Throwable e) {
            }
            map.get(Thread.currentThread().getName());
            System.out.println("线程" + Thread.currentThread().getName() + " 读取成功");
        }finally {
            lock.readLock().unlock();
        }
    }


    public void clear() {
        map.clear();
    }

}


/**
 * @Author chenglezheng
 * @Date 2020/10/22 10:01
 */
public class ReadAndWriteLock {
    static MyCache myCache = new MyCache();

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myCache.get();
            }, Integer.toString(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myCache.put();
            }, Integer.toString(i)).start();
        }
       /* while (Thread.activeCount()>2){
            Thread.yield();
        }*/
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }


}
