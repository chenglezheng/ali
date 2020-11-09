package com.qww.clz.other.lock;


import java.util.concurrent.*;

class ShareResource1 {

    private Object lock1 = new Object();

    private Integer ticketNumber=0;

    private final static int MAX_VALUE=100;


    public void concumer() throws InterruptedException {
        synchronized (lock1) {
            while (ticketNumber<=0) {
                /*System.out.println("等待，释放锁，"+Thread.currentThread().getName()+"进入锁等待池!");*/
                lock1.wait();
            }
            ticketNumber-=10;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            System.out.println("消费完，库里已有"+ticketNumber+"产品!");
            lock1.notifyAll();
        }
    }

    public void producer() throws InterruptedException {
        synchronized (lock1) {
            while (ticketNumber>MAX_VALUE) {
                /*System.out.println("等待，释放锁，"+Thread.currentThread().getName()+"进入锁等待池!");*/
                lock1.wait();
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            ticketNumber+=10;
            System.out.println("生产完，库里已有个"+ticketNumber+"产品！");
            lock1.notifyAll();
        }

    }


}


/**
 * @Author chenglezheng
 * @Date 2020/11/6 10:10
 */
public class SynchronizationNotifyDemo {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors()/2,
                Runtime.getRuntime().availableProcessors() + 1
                , 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(Runtime.getRuntime().availableProcessors() * 4),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        ShareResource1 shareResource1 = new ShareResource1();
        try{
            System.out.println("用户开始抢票！");
            for (int i = 0; i <3 ; i++) {
                executorService.submit(()->{
                    try{
                        shareResource1.concumer();
                    }catch(Exception e){

                    }
                });
            }
            System.out.println("高铁站还没放票，等着放票...");
            try{ TimeUnit.SECONDS.sleep(5); }catch(Throwable e){}
            System.out.println("高铁站开始放票!");
            for (int i = 0; i <3 ; i++) {
                executorService.submit(()->{
                    try{
                        shareResource1.producer();
                    }catch(Exception e){

                    }
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            executorService.shutdown();
        }
    }

}
