package com.github.kahlkn.demo.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.github.kahlkn.artoria.net.HttpUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EsIkDemo {

    @Test
    public void testIK() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("analyzer", "ik_smart");
//        data.put("analyzer", "ik_max_word");
        data.put("text", "中华人民共和国国歌");
        System.out.println(HttpUtils.create("http://localhost:9200/_analyze?pretty")
                .setData(JSON.toJSONString(data).getBytes()).get());
    }

    @Test
    public void testCreateIndex() throws Exception {
        System.out.println(HttpUtils.create("http://localhost:9200/index?pretty").put());
    }

    @Test
    public void test1() throws Exception {
        Map<String, Object> content = new HashMap<>();
        content.put("type", "text");
        content.put("analyzer", "ik_max_word");
        content.put("search_analyzer", "ik_max_word");
        Map<String, Object> properties = new HashMap<>();
        properties.put("content", content);
        Map<String, Object> obj = new HashMap<>();
        obj.put("properties", properties);

        System.out.println(JSON.toJSONString(obj));

        System.out.println(HttpUtils.create("http://localhost:9200/index/fulltext/_mapping -d").setContentType("application/json")
                .setData(JSON.toJSONString(obj).getBytes())
                .post());
    }

    @Test
    public void test2() throws Exception {
        Map<String, Object> obj = new HashMap<>();
        obj.put("content", "美国留给伊拉克的是个烂摊子吗");
        System.out.println(HttpUtils.create("http://localhost:9200/index/fulltext/1 -d").setContentType("application/json")
                .setData(JSON.toJSONString(obj).getBytes())
                .post());
        System.out.println(HttpUtils.create("http://localhost:9200/index/fulltext/1 -d").get());
    }

}
