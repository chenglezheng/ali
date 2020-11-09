package com.qww.clz.other.lock;

import java.util.concurrent.*;

/**
 * @Author chenglezheng
 * @Date 2020/11/3 16:10
 */
public class DeadLockDemo implements Runnable {

    private  String lockA=null;
    private  String lockB=null;

    public DeadLockDemo(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "获取"+lockA+"成功！"+ "尝试获取"+lockB+"......");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "获取"+lockB+"成功"+ "尝试获取"+lockA+"......");
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() + 1,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(Runtime.getRuntime().availableProcessors() * 2),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            String lockA="aa";
            String lockB="bb";
            service.submit(new DeadLockDemo(lockA,lockB));
            service.submit(new DeadLockDemo(lockB,lockA));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}
