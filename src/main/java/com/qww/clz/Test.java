package com.qww.clz;

import com.qww.clz.other.enumTest.EnumTest;

/**
 * @Author chenglezheng
 * @Date 2020/6/29 9:39
 */
public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(EnumTest.EnumTest_Status(i));
        }
    }
}
