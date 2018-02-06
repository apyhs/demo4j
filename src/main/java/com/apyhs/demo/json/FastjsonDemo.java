package com.apyhs.demo.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import org.quartz.JobDataMap;

import java.util.HashMap;
import java.util.Map;

public class FastjsonDemo {

    @Test
    public void testToJsonString() {
        Map<String, Map<String, Integer>> data = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("long", 15);
        map.put("num", 20);
        map.put("num1", null);
        data.put("one", map);
        System.out.println(JSON.toJSONString(data));
        // 打印为null
        System.out.println(JSON.toJSONString(data, SerializerFeature.WriteMapNullValue));
    }

    @Test
    public void testParseMap() {
//        String input = "{\"one\":{\"num\":20,\"long\":15}}";
        String input = "{\"one\":{\"num\":20,\"num1\":null,\"long\":15},\"one1\":null}";
        Map<String, Map<String, Integer>> data = JSON.parseObject(input, new TypeReference<Map<String, Map<String, Integer>>>() { });
        System.out.println(data.get("one"));
        System.out.println(data.get("one").get("long"));
        System.out.println(data.get("one").get("num"));
        System.out.println(data);
    }

    @Test
    public void test1() {
        JobDataMap jobDataMap = JSON.parseObject("{}", JobDataMap.class);
        System.out.println(jobDataMap.get(""));
    }

}
