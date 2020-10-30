package com.qww.clz.other.threadpool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author chenglezheng
 * @Date 2020/10/21 10:38
 */
public class ThreadPoolTest {


    public static void main(String[] args) throws Exception {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 1208424043; i < 1208424090; i++) {
            integers.add(i);
        }

        /*testCountDownLatch(integers);*/

        /*testFutureTask(integers);*/

        /*CyclicBarrierTest(integers);*/

        /*semaphoreTest(integers);*/
    }

    public static void semaphoreTest(ArrayList<Integer> integers) {
        Semaphore semaphore = new Semaphore(1,false);
        ExecutorService executors = Executors.newFixedThreadPool(10);
        for (int i = 0; i < integers.size(); i++) {
            executors.submit(new TransformerMeterData3(i, semaphore));
        }
        executors.shutdown();
    }

    public static void CyclicBarrierTest(ArrayList<Integer> integers) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(integers.size(), () -> {
            System.out.println("开始准备数据......");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Throwable e) {
            }
            System.out.println("数已准备好，各线程可以开始迁移了！");
        });

        ExecutorService executorService = Executors.newFixedThreadPool(integers.size());
        for (int i = 0; i < integers.size(); i++) {
            executorService.submit(new TransformerMeterData2(i, cyclicBarrier));
        }
        executorService.shutdown();
    }

    public static void testFutureTask(ArrayList<Integer> integers) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<FutureTask> futureTasks = new ArrayList<>();
        for (Integer i : integers) {
            FutureTask<Boolean> futureTask = new FutureTask<>(new TransformerMeterData1(i));
            executorService.execute(futureTask);
            futureTasks.add(futureTask);
        }
        while (futureTasks.size() > 0) {
            Iterator<FutureTask> iterator = futureTasks.iterator();
            while (iterator.hasNext()) {
                FutureTask futureTask = iterator.next();
                if (futureTask.isDone()) {
                    iterator.remove();
                }
            }
        }
        executorService.shutdown();
        System.out.println("迁移成功！");
    }

    public static void testCountDownLatch(ArrayList<Integer> integers) {
        ExecutorService executors = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(integers.size());
        for (Integer i : integers) {
            executors.execute(new TransformerMeterData(i, countDownLatch));
        }
        try {
            countDownLatch.await();
            executors.shutdown();
        } catch (Exception e) {
        }
        System.out.println(countDownLatch.getCount());
        System.out.println("数据迁移完成！开始干活......");
    }


}
