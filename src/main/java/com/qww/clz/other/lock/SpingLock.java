package com.qww.clz.other.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author chenglezheng
 * @Date 2020/10/21 15:13
 */
public class SpingLock {

    AtomicReference<Thread> threadAtomicReference=new AtomicReference<>();

    public void myLock(){
        Thread thread=Thread.currentThread();
        System.out.println(thread.getName()+"开始试图加锁中......");
        while (!threadAtomicReference.compareAndSet(null,thread)){
            System.out.println(thread.getName()+"加锁失败，尝试重新获取锁......");
            try {
                TimeUnit.SECONDS.sleep(5);
            }catch (Throwable e){}
        }
        System.out.println(thread.getName()+"加锁成功!");
    }

    public void unMyLock(){
        threadAtomicReference.compareAndSet(Thread.currentThread(),null);
        System.out.println(Thread.currentThread().getName()+"解锁成功!");
    }

    public static void main(String[] args) {

        SpingLock spingLock=new SpingLock();
        new Thread(()->{
            spingLock.myLock();
            try {
                TimeUnit.SECONDS.sleep(50);
            }catch (Throwable e){}
            finally {
                spingLock.unMyLock();
            }
        },"AA").start();
        try {
            Thread.sleep(1000);
        }catch (Exception e){}


        new Thread(()->{
            spingLock.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            }catch (Throwable e){}
            finally {
                spingLock.unMyLock();
            }
        },"BB").start();
    }


}
