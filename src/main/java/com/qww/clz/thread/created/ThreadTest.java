package com.qww.clz.thread.created;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 16:20
 */
public class ThreadTest {

    @Test
    public void createByThread(){
        Thread thread=new MyThread();
        thread.start();
    }


    @Test
    public void createdByRunnable(){
        Thread thread=new Thread(new MyRunnable());
        thread.start();
    }


    @Test
    public void createdByCallable() throws Exception{
        FutureTask<String> task=new FutureTask<String>(new MyCallable());
        Thread thread=new Thread(task);
        thread.start();
        while (!task.isDone()){
            System.out.println("线程没有执行完!");
        }
        System.out.println(task.get());
        System.out.println("主线程是否阻塞了！");
    }
    
    
    @Test
    public void excetor(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i <20 ; i++) {
            executorService.execute(new MyRunnable());
        }
    }
    
}
