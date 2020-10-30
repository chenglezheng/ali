package com.qww.clz.other.threadpool;

import java.util.HashSet;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/10/21 10:43
 */
public class TransformerMeterData2 implements Runnable{

    private Integer integer;

    private CyclicBarrier cyclicBarrier;

    public
    TransformerMeterData2(Integer integer, CyclicBarrier cyclicBarrier) {
        this.integer = integer;
        this.cyclicBarrier=cyclicBarrier;
    }

    @Override
    public void run() {
        try{
            cyclicBarrier.await();
        }catch(Exception e){
        }
        System.out.println(Thread.currentThread().getName()+"--- 【电表id"+integer+"数据迁移完成！】");

    }
}
