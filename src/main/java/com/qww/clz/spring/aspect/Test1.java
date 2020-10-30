package com.qww.clz.spring.aspect;


import com.qww.clz.spring.annotation.RunTime;
import org.springframework.stereotype.Component;

/**
 * @Author chenglezheng
 * @Date 2020/9/14 16:29
 */

@Component
@RunTime
public class Test1 {


    public void test(){
        System.out.println("11");
    }



    public String test1(){
        System.out.println("22");
        return "";
    }

    public void test2(String a){
        System.out.println("33");
    }

    @RunTime
    public void test3(String a){
        System.out.println("44");
    }


    public void test4(Test1 a){
        System.out.println("55");
    }

    public void test5(Test1 a){
        System.out.println("66");
    }
}
