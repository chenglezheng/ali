package com.qww.clz.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author chenglezheng
 * @Date 2020/9/21 11:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface CalcParamCheck {

}
