package com.qww.clz.other.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/10/21 10:43
 */
public class TransformerMeterData implements Runnable{

    private Integer integer;

    private CountDownLatch countDownLatch;

    public TransformerMeterData(Integer integer, CountDownLatch countDownLatch) {
        this.integer = integer;
        this.countDownLatch=countDownLatch;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (Throwable e){}
        System.out.println(Thread.currentThread().getName()+"--- 【电表id"+integer+"数据迁移完成！】");
        countDownLatch.countDown();
    }
}
