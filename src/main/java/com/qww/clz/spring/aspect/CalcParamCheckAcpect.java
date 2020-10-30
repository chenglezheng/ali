package com.qww.clz.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author chenglezheng
 * @Date 2020/10/12 10:17
 */
@Order(2)
@Aspect
@Component
public class CalcParamCheckAcpect {

    /**
     * 公用切点表达式
     */
    @Pointcut("@annotation(com.qww.clz.spring.annotation.CalcParamCheck)")
    public void setPoint(){

    }

    @Around(value=("setPoint()"))
    public Object CalcParamCheckAround(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("A环绕通知前");
        Object proceed = joinPoint.proceed();
        System.out.println("A环绕通知后");
        return (Integer)proceed+100000;
    }

//    @Before(value=("setPoint()"))
//    public void CalcParamCheckBefore(JoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        Class declaringType = signature.getDeclaringType();
//        Field[] declaredFields = declaringType.getDeclaredFields();
//        Method[] methods = declaringType.getMethods();
//        for (int i = 0; i < methods.length; i++) {
//            Method method=methods[i];
//            System.out.println("方法名:"+method.getName());
//            Class<?>[] parameterTypes = method.getParameterTypes();
//            for (int j = 0; j < parameterTypes.length; j++) {
//                System.out.println("参数类型："+parameterTypes[0]);
//            }
//        }
//        System.out.println("前置通知");
//    }
//
//    @After(value=("setPoint()"))
//    public void CalcParamCheckAfter(JoinPoint joinPoint){
//        System.out.println("后置通知");
//    }
//
//    @AfterThrowing(value = "setPoint()")
//    public void CalcParamCheckException(JoinPoint joinPoint){
//        System.out.println("异常通知");
//    }

}
