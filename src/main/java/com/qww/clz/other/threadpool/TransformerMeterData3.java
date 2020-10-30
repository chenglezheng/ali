package com.qww.clz.other.threadpool;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/10/21 10:43
 */
public class TransformerMeterData3 implements Runnable{

    private Integer integer;

    private Semaphore semaphore;

    public TransformerMeterData3(Integer integer, Semaphore semaphore) {
        this.integer = integer;
        this.semaphore=semaphore;
    }

    @Override
    public void run() {
        try{
            semaphore.acquire();
        }catch(Exception e){
        }
        System.out.println(Thread.currentThread().getName()+"--- 【电表id"+integer+"开始迁移数据！】");
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (Throwable e){}
        System.out.println(Thread.currentThread().getName()+"--- 【电表id"+integer+"数据迁移完成！】");
        semaphore.release();
    }
}
