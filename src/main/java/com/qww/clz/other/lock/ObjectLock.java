package com.qww.clz.other.lock;

/**
 * @Author chenglezheng
 * @Date 2020/7/2 18:53
 */
public class ObjectLock {

    private Object object=new Object();


    public void object(){
        synchronized (object){
            System.out.println("变量对象锁");
            try {
                Thread.sleep(6000);
            }catch (Exception e){

            }
        }
    }

    public void thisObject(){
        synchronized (this){
            System.out.println("this对象锁");
            try {
                Thread.sleep(6000);
            }catch (Exception e){

            }
        }
    }


    public synchronized void method(){
        System.out.println("方法对象锁");
        try {
            Thread.sleep(6000);
        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            ObjectLock objectLock=new ObjectLock();
            new Thread(()->{
                objectLock.method();
               /* objectLock.thisObject();
                objectLock.object();*/
            }).start();
        }

    }



}
