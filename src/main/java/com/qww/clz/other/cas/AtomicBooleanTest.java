package com.qww.clz.other.cas;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author chenglezheng
 * @Date 2020/7/3 10:33
 */
public class AtomicBooleanTest implements Runnable{


    private static AtomicBoolean flag=new AtomicBoolean(true);

    public static void main(String[] args) {
        AtomicBooleanTest atomicBooleanTest=new AtomicBooleanTest();
        Thread thread1=new Thread(atomicBooleanTest);
        Thread thread2=new Thread(atomicBooleanTest);
        thread1.start();
        thread2.start();
    }



    public void run() {
        System.out.println("thread:"+Thread.currentThread().getName()+";flag"+flag.get());
        if(flag.compareAndSet(true,false)){
            System.out.println(Thread.currentThread().getName()+""+flag.get());
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            flag.set(true);
        }else{
            System.out.println("重试机制thread"+Thread.currentThread().getName()+";flag"+flag.get());
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            run();
        }



    }
}
