package com.qww.clz.other.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/11/3 11:03
 */
public class QueueTest {


    public static void main(String[] args) throws Exception{

        BlockingQueue<String> blockingQueue=new ArrayBlockingQueue<>(2);
        //抛异常
        System.out.println("---------------------抛出异常------------------");
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //返回特殊值
        System.out.println("---------------------返回特殊值------------------");
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.peek());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //阻塞
        System.out.println("---------------------阻塞------------------");
        blockingQueue.put("a");
        blockingQueue.put("b");
        /*blockingQueue.put("c");*/

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        /*blockingQueue.take();*/
        //超时
        System.out.println("---------------------超时------------------");
        blockingQueue.offer("1",1,TimeUnit.SECONDS);
        blockingQueue.offer("2",1,TimeUnit.SECONDS);
        blockingQueue.offer("3",1,TimeUnit.SECONDS);
        blockingQueue.offer("4",1,TimeUnit.SECONDS);
        blockingQueue.offer("5",1,TimeUnit.SECONDS);


        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));

    }




}
