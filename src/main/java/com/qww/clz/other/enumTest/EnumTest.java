package com.qww.clz.other.enumTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author chenglezheng
 * @Date 2020/10/29 11:04
 */
@Getter
@AllArgsConstructor
public enum EnumTest {

    SUCCUESS(1, "成功"), ERROR(2, "失败");

    private int code;
    private String status;

    public static String EnumTest_Status(int index) {
        EnumTest[] values = EnumTest.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getCode() == index) {
                return values[i].getStatus();
            }
        }
        return null;
    }


}
