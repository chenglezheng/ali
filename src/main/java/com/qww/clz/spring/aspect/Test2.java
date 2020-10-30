package com.qww.clz.spring.aspect;

import com.qww.clz.spring.annotation.RunTime;
import org.springframework.stereotype.Component;

/**
 * @Author chenglezheng
 * @Date 2020/9/15 10:21
 */

@RunTime
@Component
public class Test2 {

    @RunTime
    public void test5(Test1  a){
        System.out.println("66");
    }
}
