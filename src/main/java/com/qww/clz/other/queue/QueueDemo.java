package com.qww.clz.other.queue;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class ShareResource {

    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<Integer> blockingQueue = null;

    public ShareResource(BlockingQueue<Integer> blockingQueue) {
        System.out.println("实例化类名："+blockingQueue.getClass().getName());
        this.blockingQueue = blockingQueue;
    }

    /**
     * 生产者
     */
    public void product() throws Exception {
        Integer data = null;
        boolean insertFlag = false;
        while (flag) {
            data = atomicInteger.incrementAndGet();
            insertFlag = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (insertFlag) {
                System.out.println("[生产者成功生产一个数据]:" + data);
            } else {
                System.out.println("[生产者为未能成功生产一个数据]:" + data);
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
        }
    }


    public void consumer() throws Exception {
        Integer a = null;
        while (flag) {
            a = blockingQueue.poll(3, TimeUnit.SECONDS);
            if ("".equals(a) || a == null) {
                return;
            }
            System.out.println("[消费者成功消费一个数据]:" + a);
        }
    }

    public void stop() {
        this.flag = false;
        System.out.println("领导喊停！");
    }

}


/**
 * @Author chenglezheng
 * @Date 2020/11/3 11:25
 */
public class QueueDemo {
    static ShareResource shareResource = new ShareResource(new LinkedBlockingQueue<>(10));

    public static void main(String[] args) {
        System.out.println("CPU核心线程数" + Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors() + 1,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        /*ExecutorService executorService = Executors.newFixedThreadPool(10);*/

        try {
            executorService.submit(() -> {
                try {
                    System.out.println("[生产者已启动]");
                    shareResource.product();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executorService.submit(() -> {
                try {
                    System.out.println("[消费者已启动]");
                    shareResource.consumer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }


        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Throwable e) {
        }

        shareResource.stop();
    }


}
