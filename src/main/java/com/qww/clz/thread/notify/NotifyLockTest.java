package com.qww.clz.thread.notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author chenglezheng
 * @Date 2020/9/15 14:37
 */
public class NotifyLockTest {

    Lock lock=new ReentrantLock();

    Condition condition=lock.newCondition();

    private int i=0;

    public void odd(){
        try {
           lock.lock();
            while (i<10){
                if(i%2==0){
                    System.out.println("偶数线程打印"+i);
                    i++;
                    condition.signal();
                }else{
                    try {
                        condition.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public void edd(){
        try {
            lock.lock();
            while (i<10){
                if(i%2!=0){
                    System.out.println("奇数线程打印"+i);
                    i++;
                    condition.signal();
                }else{
                    try {
                        condition.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        NotifyLockTest notifyLockTest =new NotifyLockTest();
        new Thread(()->{
            notifyLockTest.odd();
        }).start();
        new Thread(()->{
            notifyLockTest.edd();
        }).start();
    }
}
