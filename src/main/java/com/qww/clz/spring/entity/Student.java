package com.qww.clz.spring.entity;

import lombok.Data;

/**
 * @Author chenglezheng
 * @Date 2020/9/17 9:24
 */

@Data
public class Student {

    private String name;

    private int age;

    private long num;


    public void initMethod(){
        System.out.println("初始化");
    }

    public void destroy(){
        System.out.println("销毁");
    }


}
