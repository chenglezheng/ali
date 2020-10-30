package com.qww.clz.other.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/10/21 10:43
 */
public class TransformerMeterData1 implements Callable<Boolean> {

    private Integer integer;


    public TransformerMeterData1(Integer integer) {
        this.integer = integer;
    }

    @Override
    public Boolean call() throws Exception  {
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (Throwable e){}
        System.out.println(Thread.currentThread().getName()+"--- 【电表id"+integer+"数据迁移完成！】");
        return true;
    }
}
