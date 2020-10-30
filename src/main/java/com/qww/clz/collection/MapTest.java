package com.qww.clz.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author chenglezheng
 * @Date 2020/7/1 16:23
 */
public class MapTest {


    @Test
    public void mapTest1(){
        Map<String,String> map=new ConcurrentHashMap<String, String>();

        map.put("111","222");
        System.out.println(map.get("111"));


    }




}
