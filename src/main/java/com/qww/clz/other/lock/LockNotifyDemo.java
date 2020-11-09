package com.qww.clz.other.lock;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class ShareResource{

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private String flag = "1";


    public void condition3() {
        lock.lock();
        try {
            String string3 = "3";
            while (string3!=flag){
                condition3.await();
            }
            System.out.println("condition3" + "干活中...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            System.out.println("condition3" + "干完了，让1干");
            flag="1";
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void condition2() throws InterruptedException {
        lock.lock();
        try{
            String string2 = "2";
            while (string2!=flag){
                condition2.await();
            }
            System.out.println("condition2" + "干活中...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            System.out.println("condition2" + "干完了，让3干");
            flag="3";
            condition3.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void condition1() throws InterruptedException {
        lock.lock();
        try{
            String string1 = "1";
            while (flag!=string1){
                condition1.await();
            }
            System.out.println("condition1" + "干活中...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            System.out.println("condition1" + "干完了，让2干");
            flag="2";
            condition2.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

}


/**
 * @Author chenglezheng
 * @Date 2020/11/3 17:13
 */
public class LockNotifyDemo{

    public static void main(String[] args){
        ExecutorService executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() + 1
                , 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(Runtime.getRuntime().availableProcessors() * 4),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        try{
            ShareResource shareResource=new ShareResource();
            for (int i = 0; i <5 ; i++) {
                executorService.submit(()->{
                    try{
                        shareResource.condition1();
                    }catch(Exception e){

                    }
                });
                executorService.submit(()->{
                    try{
                        shareResource.condition2();
                    }catch(Exception e){

                    }
                });executorService.submit(()->{
                    try{
                        shareResource.condition3();
                    }catch(Exception e){

                    }
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            executorService.shutdown();
        }

    }

}
