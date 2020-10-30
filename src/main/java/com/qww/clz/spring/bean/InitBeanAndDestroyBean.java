package com.qww.clz.spring.bean;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author chenglezheng
 * @Date 2020/9/17 9:36
 */

public class InitBeanAndDestroyBean implements BeanPostProcessor/*,InitializingBean, DisposableBean*/ {
    public String say() {
        return "Hello!" + this.getClass().getName();
    }

    public InitBeanAndDestroyBean() {
        System.out.println("执行InitBeanAndDestroyBean构造方法");
    }
/*
    @Override
    public void destroy() throws Exception {
        System.out.println("接口-执行InitBeanAndDestroyBeanTest：destroy方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("接口-执行InitBeanAndDestroyBeanTest：afterPropertiesSet方法");
    }*/

   /* @PostConstruct
    public void postConstructstroy() {
        System.out.println("注解-执行InitBeanAndDestroyBeanTest：preDestroy方法");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("注解--执行InitBeanAndDestroyBeanTest：preDestroy方法");
    }

    public void initMethod() {
        System.out.println("XML配置-执行InitBeanAndDestroyBeanTest：init-method方法");
    }

    public void destroyMethod() {
        System.out.println("XML配置-执行InitBeanAndDestroyBeanTest：destroy-method方法");
    }*/

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("处理器执行InitBeanAndDestroyBeanTest：init-method方法");
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("处理器执行InitBeanAndDestroyBeanTest：destroy-method方法");
        return o;
    }
}
