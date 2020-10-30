package com.qww.clz.other.exercise;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/10/23 15:27
 */
public class CallableExc implements Callable<String> {


    @Override
    public String call() throws Exception {
        try{ TimeUnit.SECONDS.sleep(5); }catch(Throwable e){}
        return "测试线程回调";
    }

    @Test
    public void test() throws Exception{
        FutureTask<String> futureTask=new FutureTask<>(new CallableExc());
        new Thread(futureTask).start();
        while (futureTask.isDone());
        System.out.println(futureTask.get());
    }

}
