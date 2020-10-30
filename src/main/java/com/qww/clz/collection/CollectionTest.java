package com.qww.clz.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

/**
 * @Author chenglezheng
 * @Date 2020/6/29 9:40
 */
public class CollectionTest {


    /**
     *
     *Collections的使用
     */
    @Test
    public void collectionsTest(){

        //排序
        Integer[] array = new Integer[]{3, 10, 4, 0, 2};
        List<Integer> integers = Arrays.asList(array);
        Collections.sort(integers);
        System.out.println(integers);

        //混排
        Collections.shuffle(integers);
        System.out.println(integers);


        //反转
        Collections.reverse(integers);
        System.out.println(integers);

        //填充（覆盖）元素
        Collections.fill(integers,-1);
        System.out.println(integers);
        System.out.println(Arrays.toString(array));


        //替换元素
        array = new Integer[]{3, 10, 4, 4, 4};
        integers = Arrays.asList(array);
        Collections.replaceAll(integers,4,-4);
        System.out.println(integers);


        //拷贝List
        List<Integer> integersCopy=new ArrayList<Integer>();
        integersCopy.add(1);
        integersCopy.add(1);
        integersCopy.add(1);
        integersCopy.add(1);
        integersCopy.add(1);
        integersCopy.add(1);
        integersCopy.add(1);
        Collections.copy(integersCopy,integers);
        System.out.println(integersCopy);

        //返回List中的最大值，最小值
        System.out.println(Collections.max(integers));
        System.out.println(Collections.min(integers));


        //源列表中第一次和最后一次出现指定列表的起始位置
        Integer[] array1 = new Integer[]{3, 10, 4, 0, 2, 4, 0};
        List integers1 = Arrays.asList(array1);
        Integer[] array2 = new Integer[]{4, 0};
        List integers2 = Arrays.asList(array2);
        out.println(Collections.lastIndexOfSubList(integers1, integers2)); //5
        out.println(Collections.indexOfSubList(integers1, integers2)); //2

        //循环移动
        out.println(integers);
        Collections.rotate(integers, 1); //[2, 3, 10, 4, 0] 依次右移一位
        out.println(integers);
        Collections.rotate(integers, -2); //[10, 4, 0, 2, 3] 依次左移两位
        out.println(integers);


        //二分查找，返回所在的下标。没有找到返回负的下标值。
        array = new Integer[]{0, 2, 4, 10, 20};
        integers = Arrays.asList(array);
        out.println(Collections.binarySearch(integers, 4)); //2
        out.println(Collections.binarySearch(integers, 9)); //-4


        //指定元素在集合中出现的次数
        array = new Integer[]{0, 2, 4, 4, 20};
        integers = Arrays.asList(array);
        out.println(Collections.frequency(integers, 4)); //2



        //得到对象的n份拷贝
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);
        List<List<Integer>> lists = Collections.nCopies(3, numbers);
        out.println(lists); //[[1], [1], [1]]
        numbers.add(2);
        out.println(lists); //[[1, 2], [1, 2], [1, 2]]


    }






}
