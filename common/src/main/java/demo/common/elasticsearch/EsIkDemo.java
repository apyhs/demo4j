package demo.common.elasticsearch;

import artoria.net.HttpMethod;
import artoria.net.HttpRequest;
import artoria.net.HttpResponse;
import artoria.net.HttpUtils;
import com.alibaba.fastjson.JSON;
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
        HttpRequest request = new HttpRequest();
        request.setUrl("http://localhost:9200/_analyze?pretty");
        request.setMethod(HttpMethod.GET);
        request.setBody(JSON.toJSONString(data));
        HttpResponse response = HttpUtils.getHttpClient().execute(request);
        System.out.println(response.getBodyAsString());
    }

    @Test
    public void testCreateIndex() throws Exception {
        System.out.println(HttpUtils.put("http://localhost:9200/index?pretty"));
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


        HttpRequest request = new HttpRequest();
        request.setUrl("http://localhost:9200/index/fulltext/_mapping -d");
        request.setMethod(HttpMethod.POST);
        request.setBody(JSON.toJSONString(obj));
        request.addHeader(HttpRequest.CONTENT_TYPE, "application/json");
        HttpResponse response = HttpUtils.getHttpClient().execute(request);
        System.out.println(response.getBodyAsString());
    }

    @Test
    public void test2() throws Exception {
        Map<String, Object> obj = new HashMap<>();
        obj.put("content", "美国留给伊拉克的是个烂摊子吗");

        HttpRequest request = new HttpRequest();
        request.setUrl("http://localhost:9200/index/fulltext/1 -d");
        request.setMethod(HttpMethod.POST);
        request.setBody(JSON.toJSONString(obj));
        request.addHeader(HttpRequest.CONTENT_TYPE, "application/json");
        HttpResponse response = HttpUtils.getHttpClient().execute(request);
        System.out.println(response.getBodyAsString());

        System.out.println(HttpUtils.get("http://localhost:9200/index/fulltext/1 -d"));
    }

}
