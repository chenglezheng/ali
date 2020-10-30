package com.qww.clz.thread.arraylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author chenglezheng
 * @Date 2020/10/13 9:40
 */
public class ArrayListUnSafe {



    public static void main(String[] args) throws Exception{
        final List<Integer> list=new ArrayList<>();
        final List<Double> list1=new ArrayList<>();

        /*arrayListTest1(list);*/

        /*arrayListTest(list1);*/

        /*testVeactor();*/

        /*testCollexction();*/

        testArrayList();




    }

    public static void testArrayList() {
        final List<Double> list2=new CopyOnWriteArrayList<>();

        for (int i = 0; i <30; i++) {
            new Thread(()->{
                list2.add(Math.random());
                System.out.println(list2);
            }).start();
        }
    }

    public static void testCollexction() {
        final List<Double> list2=Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i <30; i++) {
            new Thread(()->{
                list2.add(Math.random());
                System.out.println(list2);
            }).start();
        }
    }

    public static void testVeactor() {
        final List<Double> vector=new Vector<>();

        for (int i = 0; i <30; i++) {
            new Thread(()->{
                vector.add(Math.random());
                System.out.println(vector);
            }).start();
        }
    }

    public static void arrayListTest1(List<Integer> list) throws InterruptedException {
        new Thread(()->{
            for (int i = 0; i <1000 ; i++) {
                list.add(i);
                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(()->{
            for (int i = 1000; i <2000 ; i++) {
                list.add(i);
                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);

        for (int i = 0; i <list.size() ; i++) {
            System.out.println("第" + (i + 1) + "个元素为：" + list.get(i));
        }
    }

    public static void arrayListTest(List<Double> list) {
        for (int i = 0; i <3; i++) {
            new Thread(()->{
                list.add(Math.random());
                System.out.println(list);
            }).start();
        }
    }

}
