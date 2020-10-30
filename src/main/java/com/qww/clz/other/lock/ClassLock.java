package com.qww.clz.other.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/10/21 15:49
 */
public class ClassLock {

    private static Object lock=new Object();


    public void object(){
        synchronized (lock){
            System.out.println("变量类锁");
            try{ TimeUnit.SECONDS.sleep(2); }catch(Throwable e){}
        }
    }

    public static synchronized void method(){
        System.out.println("方法类锁");
        try{ TimeUnit.SECONDS.sleep(2); }catch(Throwable e){}
    }

    public void classLock(){
        synchronized (ClassLock.class){
            System.out.println("类锁");
            try{ TimeUnit.SECONDS.sleep(2); }catch(Throwable e){}
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            ClassLock classLock=new ClassLock();
            new Thread(()->{
//                classLock.object();
                classLock.classLock();
                classLock.method();
            }).start();
        }
    }


}
