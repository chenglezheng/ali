package com.qww.clz.other.threadpool;

import java.util.concurrent.*;

/**
 * @Author chenglezheng
 * @Date 2020/11/3 15:42
 */
public class ThreadPoolDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService service=new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors()+1,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(6),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        /*ExecutorService service=Executors.newFixedThreadPool(10);*/
        try{
            service.submit(()->{
                for (int i = 0; i <5 ; i++) {
                    System.out.println(Thread.currentThread().getName()+"[11111]");
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){

                    }
                }
            });
            service.submit(()->{
                for (int i = 0; i <5 ; i++) {
                    System.out.println(Thread.currentThread().getName()+"[22222]");
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){

                    }
                }
            });
            service.submit(()->{
                for (int i = 0; i <5 ; i++) {
                    System.out.println(Thread.currentThread().getName()+"[33333]");
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){

                    }
                }
            });
            service.submit(()->{
                for (int i = 0; i <5 ; i++) {
                    System.out.println(Thread.currentThread().getName()+"[44444]");
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){

                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            service.shutdown();
        }

    }


}
