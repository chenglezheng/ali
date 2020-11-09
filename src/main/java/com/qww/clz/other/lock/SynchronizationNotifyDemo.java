package com.qww.clz.other.lock;


import java.util.concurrent.*;

class ShareResource1 {

    private Object lock1 = new Object();

    private Integer ticketNumber=0;


    public void concumer() throws InterruptedException {
        synchronized (lock1) {
            while (ticketNumber<=0) {
                lock1.wait();
            }
            System.out.println("ticketNumber:"+ticketNumber+"开始消费了");
            ticketNumber=0;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            System.out.println("ticketNumber:"+ticketNumber+"消费完了");
            lock1.notify();
        }
    }

    public void producer() throws InterruptedException {
        synchronized (lock1) {
            while (ticketNumber>10) {
                lock1.wait();
            }
            System.out.println("ticketNumber:"+ticketNumber+"开始生产了");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Throwable e) {
            }
            ticketNumber+=10;
            System.out.println("ticketNumber:"+ticketNumber+"生产完了");
            lock1.notify();
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
                Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() + 1
                , 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(Runtime.getRuntime().availableProcessors() * 4),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        ShareResource1 shareResource1 = new ShareResource1();
        try{
            for (int i = 0; i <5 ; i++) {
                executorService.submit(()->{
                    try{
                        shareResource1.producer();
                    }catch(Exception e){

                    }
                });
            }
            for (int i = 0; i <5 ; i++) {
                executorService.submit(()->{
                    try{
                        shareResource1.concumer();
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
