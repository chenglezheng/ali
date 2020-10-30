package com.qww.clz.thread.volatileTest;

/**
 * @Author chenglezheng
 * @Date 2020/9/7 11:06
 */
public class DLCTest {

    private /*volatile*/ static DLCTest dlcTest=null;


    private DLCTest() {
    }

    public static DLCTest getInstance() {
        if(dlcTest==null){
            synchronized (DLCTest.class){
                dlcTest=new DLCTest();   //a.分配内存  b.初始化对象 c.设置dlcTest指向刚分配内存的地址
                                        // 如果没有volatile修饰 a->b->c 有可能会变成  a->c->b 编译器和处理器允许重排序，为提高性能。多线程下就会出现问题
                                        // A线程走完该行程序后，B线程进来会发现dlcTest不为空，有地址指向，但是对象还未初始化。此时B线程就会得到一个未初始化的对象
                                        // 如果有volatile修饰则，volatile会禁止重排序。以达到线程安全的目的
            }
        }
        return dlcTest;
    }

    public void testSysOut(){
        System.out.println("获取实例成功！");
    }


    public static void main(String[] args) {

        new Thread(()->{
            DLCTest dlcTest=DLCTest.getInstance();

        }).start();

        new Thread(()->{
            DLCTest dlcTest1=DLCTest.getInstance();
            dlcTest1.testSysOut();
        }).start();
    }


}
