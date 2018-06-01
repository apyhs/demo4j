package com.github.kahlkn.demo4j.common.jdk;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {

    @Test
    public void test1() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(8, 87677876, true);
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }

        System.out.println(map.get("2"));
        System.out.println("---------------------------");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
    }


}
