package com.qww.clz.thread.threadLocal;

import com.qww.clz.Test;

/**
 * @Author chenglezheng
 * @Date 2020/7/6 19:24
 */
public class ThreadLocalTest {

    ThreadLocal<Long> longLocal=new ThreadLocal<Long>(){
        protected Long initialValue() {
            return 1l;
        }
    };
    ThreadLocal<String> stringThreadLocal=new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return super.initialValue();
        }
    };


    public void set(){
        longLocal.set(Thread.currentThread().getId());
        stringThreadLocal.set(Thread.currentThread().getName());
    }

    public long getLong(){
        return longLocal.get();
    }


    public String getString(){
        return stringThreadLocal.get();
    }


    public static void main(String[] args) throws Exception{
        final ThreadLocalTest threadLocalTest=new ThreadLocalTest();


        threadLocalTest.set();

        System.out.println("first****"+threadLocalTest.getLong());
        System.out.println("first****"+threadLocalTest.getString());

        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                threadLocalTest.set();
                System.out.println("****"+threadLocalTest.getLong());
//                System.out.println("second****"+threadLocalTest.getString());
            }).start();
        }

        while (Thread.activeCount()>2)
        Thread.yield();

        System.out.println("third****"+threadLocalTest.getLong());
        System.out.println("third****"+threadLocalTest.getString());

    }



}
