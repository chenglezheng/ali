package com.qww.clz.thread.threadLocal;

import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/9/8 19:06
 */
public class CreateThreadBackUp implements Runnable{

   String name="";  //线程不安全

    ThreadLocal<String> threadLocal=new ThreadLocal<>();

    @Override
    public void run() {
        name=Thread.currentThread().getName();
        threadLocal.set(Thread.currentThread().getName());
        for (int i = 0; i <10 ; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){}
            System.out.println("普通类型："+i+Thread.currentThread().getName()+":"+name);
            System.out.println("ThreadLocal包装："+i+Thread.currentThread().getName()+":"+threadLocal.get());
        }
    }


    public static void main(String[] args) {
        CreateThreadBackUp backUp=new CreateThreadBackUp();
        new Thread(backUp,"AAA").start();
        new Thread(backUp,"BBB").start();
    }

}
