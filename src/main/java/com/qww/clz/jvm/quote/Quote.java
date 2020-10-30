package com.qww.clz.jvm.quote;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

/**
 * @Author chenglezheng
 * @Date 2020/10/9 10:17
 */
public class Quote {

    @Test
    public void testStrongQuote(){
        Object object1=new Object();
        Object object2=object1;
        System.out.println(object1);
        System.out.println(object2);
        System.out.println("-----------------------");
        System.gc();
        System.out.println(object1);
        System.out.println(object2);
    }

    /**
     * jvm配置参数：-Xms250m -Xmx250m -XX:+PrintGCDetails
     */
    @Test
    public void testSoftQuote(){
        Object object1=new Object();
        SoftReference<Object> softReference=new SoftReference<>(object1);
        System.out.println(object1);
        System.out.println(softReference.get());
        System.out.println("---------------------------");
        System.gc();
        object1=null;
        try {
            byte[] bytes=new byte[1024*1024*1000];
        }catch (Exception e){

        }finally {
            System.out.println(object1);
            System.out.println(softReference.get());
        }
    }

    @Test
    public void testWeakQuote(){
        Object object=new Object();
        WeakReference<Object> weakReference=new WeakReference<>(object);
        System.out.println(object);
        System.out.println(weakReference.get());
        System.out.println("--------------------------");
        object=null;
        System.gc();
        System.out.println(object);
        System.out.println(weakReference.get());
    }

    @Test
    public void testWeakHashMap(){
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer integer=new Integer(1);
        String value="weakHashMap";
        map.put(integer,value);
        System.out.println(map);
        System.out.println(map.get(1));
        System.out.println("--------------------");
        integer=null;
        System.gc();
        System.out.println(map);
        System.out.println(map.get(1));
    }


    @Test
    public void testPhantomTest(){
        Object object=new Object();
        ReferenceQueue<Object> objectReferenceQueue=new ReferenceQueue<>();
        PhantomReference<Object> phantomReference=new PhantomReference<>(object,objectReferenceQueue);
        object=null;
        System.out.println(object);
        System.out.println(objectReferenceQueue.poll());
        System.out.println(phantomReference.get());
        System.out.println("+++++++++++++++++++++++++++++++");
        System.gc();
        System.out.println(object);
        System.out.println(objectReferenceQueue.poll());
        System.out.println(phantomReference.get());

    }

}
