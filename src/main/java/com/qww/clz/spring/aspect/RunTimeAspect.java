package com.qww.clz.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author chenglezheng
 * @Date 2020/10/14 9:16
 */

@Order(3)
@Aspect
@Component
public class RunTimeAspect {


    @Before(value = "@annotation(com.qww.clz.spring.annotation.RunTime)")
    public void methodRunTimeBefore(JoinPoint joinPoint) throws Throwable{
        System.out.println("B方法前置通知");
    }


    @After(value = "@annotation(com.qww.clz.spring.annotation.RunTime)")
    public void methodRunTimeAfter(JoinPoint joinPoint) throws Throwable{
        System.out.println("B方法后置通知");
    }

}
