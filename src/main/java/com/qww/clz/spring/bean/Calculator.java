package com.qww.clz.spring.bean;

import com.qww.clz.spring.annotation.CalcParamCheck;
import com.qww.clz.spring.annotation.RunTime;
import org.springframework.stereotype.Component;

/**
 * @Author chenglezheng
 * @Date 2020/10/12 10:47
 */

@Component
public class Calculator {


    @CalcParamCheck
    @RunTime
    public Integer plus(Integer a,Integer b){
        System.out.println("主方法："+a+"+"+b+"="+(a+b));
        return a+b;
    }

    @CalcParamCheck
    public void subtract(Integer a,Integer b){
        System.out.println(a+"-"+b+"="+(a-b));
    }


    @CalcParamCheck
    public void multiply(Integer a,Integer b){
        System.out.println(a+"×"+b+"="+(a*b));
    }

    @CalcParamCheck
    public void divide(Integer a,Integer b){
        System.out.println(a+"÷"+b+"="+(a/b));
    }


}
