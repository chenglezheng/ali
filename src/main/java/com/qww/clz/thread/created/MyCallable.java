package com.qww.clz.thread.created;

import java.util.concurrent.Callable;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 16:24
 */
public class MyCallable implements Callable<String > {


    @Override
    public String call() throws Exception {
        Thread.sleep(1111);
        System.out.println(MyCallable.class);
        System.out.println(Thread.currentThread().getName() +"******"+System.currentTimeMillis());
        if(true){
            return "获取任务成功！";
        }else {
            return "失败";
        }
    }
}
