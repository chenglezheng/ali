package com.qww.clz.thread.threadLocal;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/9/9 14:44
 */
public class ThreadCountTest implements Runnable{


    static Set<V<Integer>> set=new HashSet<>();

    synchronized static void addSet(V<Integer> v){
        set.add(v);
        System.out.println("我被线程"+Thread.currentThread().getName()+"调用了!!!");
    }


    static ThreadLocal<V<Integer>> threadLocal=new ThreadLocal<V<Integer>>(){
        @Override
        protected V<Integer> initialValue() {
            V<Integer> v=new V<Integer>();
            v.setT(0);
            addSet(v);
            return v;
        }
    };

    static int j=1;


    @Override
    public void run() {
        for (int i = 0; i <100 ; i++) {
            threadLocal.get().setT(threadLocal.get().getT()+1);
            j++;
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadCountTest threadCountTest=new ThreadCountTest();
        for (int i = 0; i <10 ; i++) {
            new Thread(threadCountTest).start();
        }
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(threadCountTest.set.stream().map(x->x.getT()).reduce((a,x)->a+x).get());
        System.out.println(threadCountTest.j);

    }

}
