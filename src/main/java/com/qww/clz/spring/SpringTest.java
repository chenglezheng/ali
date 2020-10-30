package com.qww.clz.spring;

import com.qww.clz.spring.aspect.Test1;
import com.qww.clz.spring.bean.Calculator;
import com.qww.clz.spring.bean.InitBeanAndDestroyBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application.xml"})
public class SpringTest {

    @Autowired
    private Test1 test1;


//    @Resource
//    private ThreadPoolTaskExecutor executor;

    @Test
    public void test(){
       /* test1.test();
        System.out.println("----------------------------------");
        test1.test1();
        System.out.println("----------------------------------");
        test1.test2("111");
        System.out.println("----------------------------------");*/
        test1.test3("111111");
        System.out.println("----------------------------------");
    }

    @Test
    public void threadTest(){

//        for (int i = 0; i < 1; i++) {
//            executor.submit(()->{
//                try {
//                    TimeUnit.SECONDS.sleep(1000);
//                    System.out.println("1111");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                System.out.println("线程执行完毕");
//            });
//        }
//
//        System.out.println("1111");
    }


//    @Resource
//    private InitBeanAndDestroyBean initBeanAndDestroyBean;
//
//    @Test
//    public void initAndDestroyTest(){
//        System.out.println(initBeanAndDestroyBean.say());
//    }

    @Autowired
    private Calculator calculator;

    @Test
    public void testCalculator(){
        System.out.println("后置处理后数据为："+calculator.plus(100,123));
    }


}
