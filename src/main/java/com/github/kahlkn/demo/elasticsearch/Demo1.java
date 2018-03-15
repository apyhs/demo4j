package com.github.kahlkn.demo.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.github.kahlkn.artoria.net.HttpUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Demo1 {

    @Test
    public void test0() throws Exception {
        // 创建一个新的索引
//        System.out.println(Http.on("http://localhost:9200/blog").put());

        // 添加一条文档
        Map<String, Object> data = new HashMap<>();
        data.put("title", "test");
        data.put("content", "test content");
        System.out.println(HttpUtils.create("http://localhost:9200/blog/article/1")
                .setData(JSON.toJSONString(data).getBytes()).put());
    }

    @Test
    public void test1() throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();
        //创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        //搜索数据
        GetResponse response = client.prepareGet("blog", "article", "1").execute().actionGet();
        //输出结果
        System.out.println(response.getSourceAsString());
        //关闭client
        client.close();
    }

}
