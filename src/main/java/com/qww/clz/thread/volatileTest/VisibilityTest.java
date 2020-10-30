package com.qww.clz.thread.volatileTest;

import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/9/7 15:58
 */


class Student{
     volatile int age=25;

    public void updateName(){
        this.age=29;
    }
}

public class VisibilityTest {


    public static void main(String[] args) {
        testVisibility();

    }


    /**
     *证明可见性
     */
    public static void testVisibility() {
        Student student = new Student();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"处理中...");
            System.out.println(Thread.currentThread().getName()+"学生年龄已经被更改为："+student.age);
            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (Exception e){e.printStackTrace();}
            student.updateName();

        },"线程1").start();

        while(student.age==25){

        }
        System.out.println("学生年龄是："+student.age);
    }


}
