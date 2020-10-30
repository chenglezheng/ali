package com.qww.clz.other.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;


@AllArgsConstructor
@Data
@ToString
class User{

    private String userName;

    private Integer age;
}



/**
 * @Author chenglezheng
 * @Date 2020/10/15 9:48
 */
public class ABATest {

    static AtomicReference<User> userAtomicReference=new AtomicReference<>();

    static Integer initStamp=new Integer(1);

    static AtomicStampedReference<Integer> userAtomicStampedReference=new AtomicStampedReference<>(100,initStamp);



    public static void main(String[] args) {
//        User user1=new User("多多老师",24);
//        User user2=new User("文文老师",25);
//        userAtomicReference.set(user1);
//        System.out.println(userAtomicReference.get().toString());
//        userAtomicReference.compareAndSet(user1,user2);
//        System.out.println(userAtomicReference.get().toString());

        new Thread(()->{
            Integer version=initStamp;
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){}
            System.out.println(Thread.currentThread().getName()+"  当前版本号："+userAtomicStampedReference.getStamp());
            userAtomicStampedReference.compareAndSet(100,101,version,++version);
            System.out.println(Thread.currentThread().getName()+"  当前版本号："+userAtomicStampedReference.getStamp());
            System.out.println(userAtomicStampedReference.compareAndSet(101,100,version,++version));
            System.out.println(Thread.currentThread().getName()+"  当前版本号："+userAtomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"  最终值："+userAtomicStampedReference.getReference());
        },"t1").start();

        new Thread(()->{
            Integer version=initStamp;
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){}
            System.out.println(Thread.currentThread().getName()+"  当前版本号："+userAtomicStampedReference.getStamp());
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (Exception e){}
            System.out.println(userAtomicStampedReference.compareAndSet(100,2019,version,++version));
            System.out.println(Thread.currentThread().getName()+"  当前版本号："+userAtomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"  最终值："+userAtomicStampedReference.getReference());
        },"t2").start();


    }

}
