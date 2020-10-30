package com.qww.clz.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author chenglezheng
 * @Date 2020/9/14 14:48
 */

@Component
@Aspect
public class AspectTest {



    @Around(value = "within(com.qww.clz.spring.aspect..*)")
    public void withinTest(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("开始withinTest");
        pjp.proceed();
        System.out.println("结束withinTest");
    }

    @Around(value = "execution(void com.qww.clz.spring.aspect..*.*(String))")
    public void executionTest(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("开始executionTest");
        pjp.proceed();
        System.out.println("结束executionTest");
    }


    @Around(value = "args(com.qww.clz.spring.aspect.Test1)")
    public void argsTest(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("开始argsTest");
        pjp.proceed();
        System.out.println("结束argsTest");
    }

    @Around(value = "this(com.qww.clz.spring.aspect.Test1)")
    public void thisTest(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("开始thisTest");
        pjp.proceed();
        System.out.println("结束thisTest");
    }

    @Around(value = "target(com.qww.clz.spring.aspect.Test1)")
    public void targetTest(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("开始targetTest");
        pjp.proceed();
        System.out.println("结束targetTest");
    }

//    @Around(value = "@annotation(com.qww.clz.spring.annotation.RunTime)")
//    public void annotationTest(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("开始annotationTest");
//        pjp.proceed();
//        System.out.println("结束annotationTest");
//    }

//    //具有@RunTime的目标对象中的任意方法
//    @Around(value = "@target(com.qww.clz.spring.annotation.RunTime)")
//    public void aiTeTest(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("开始annotationTest");
//        pjp.proceed();
//        System.out.println("结束annotationTest");
//    }
//
//
//    //具有@RunTime的目标对象中的任意方法
//    @Around(value = "@within(com.qww.clz.spring.annotation.RunTime)")
//    public void aiTeTest1(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("开始annotationTest");
//        pjp.proceed();
//        System.out.println("结束annotationTest");
//    }
//
//
//    //具有@RunTime的目标对象中的任意方法
//    @Around(value = "@args(com.qww.clz.spring.annotation.RunTime)")
//    public void aiTeTest2(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("开始annotationTest");
//        pjp.proceed();
//        System.out.println("结束annotationTest");
//    }
//
//    @Before(value = "@annotation(com.qww.clz.spring.annotation.RunTime)")
//    public void aiTeTest1() throws Throwable {
//        return ;
//    }
}
