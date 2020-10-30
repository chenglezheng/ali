package com.qww.clz.thread.dead;

/**
 * @Author chenglezheng
 * @Date 2020/9/3 20:05
 */
public class DeadLock implements Runnable{


    private int flag;

    private static Object object1=new Object();
    private static Object object2=new Object();


    public DeadLock(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if(flag==1){
            synchronized (object1){
                System.out.println(Thread.currentThread().getName()+"已获取资源1，准备请求资源2");
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (object2){
                    System.out.println(Thread.currentThread().getName()+"已获取资源1和资源2");
                }
            }
        }else{
            synchronized (object2){
                System.out.println(Thread.currentThread().getName()+"已获取资源2，准备请求资源1");
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (object1){
                    System.out.println(Thread.currentThread().getName()+"已获取资源1和资源2");
                }
            }
        }
    }
}
