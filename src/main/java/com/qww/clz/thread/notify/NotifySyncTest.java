package com.qww.clz.thread.notify;

/**
 * @Author chenglezheng
 * @Date 2020/9/15 14:30
 */
public class NotifySyncTest {

    private int i=0;

    private Object object=new Object();


    public void odd(){
        synchronized (object){
            while (i<10){
                if(i%2==0){
                    System.out.println("偶数线程打印"+i);
                    i++;
                    object.notify();
                }else{
                    try {
                        object.wait();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void edd(){
        synchronized (object){
            while (i<10){
                if(i%2!=0){
                    System.out.println("奇数线程打印"+i);
                    i++;
                    object.notify();
                }else{
                    try {
                        object.wait();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        NotifySyncTest notifySyncTest =new NotifySyncTest();
        new Thread(()->{
           notifySyncTest.odd();
        }).start();
        new Thread(()->{
            notifySyncTest.edd();
        }).start();
    }


}
