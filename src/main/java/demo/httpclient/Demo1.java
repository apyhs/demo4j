package demo.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    @Test
    public void test1() throws IOException {
        //HttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
//        HttpGet httpGet = new HttpGet("http://www.gxnu.edu.cn/default.html");
        HttpGet httpGet = new HttpGet("httpclient://www.baidu.com");
//        System.out.println(httpGet.getRequestLine());
            //执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (entity != null) {
                System.out.println("contentEncoding:" + entity.getContentEncoding());
                System.out.println("response content:" + EntityUtils.toString(entity));
            }
        closeableHttpClient.close();
    }

    @Test
    public void test2() throws IOException {
        Header userAgent = new BasicHeader("UserSmall-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36");
        List<Header> headers = new ArrayList<>();
        headers.add(userAgent);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultHeaders(headers).build();
        HttpGet httpGet = new HttpGet("https://p.10086.cn");
        HttpResponse httpResponse = httpClient.execute(httpGet);

        HttpEntity entity = httpResponse.getEntity();
        System.out.println("status:" + httpResponse.getStatusLine());
        if (entity != null) {
            System.out.println("contentEncoding:" + entity.getContentEncoding());
            System.out.println("response content:" + EntityUtils.toString(entity));
        }

        httpClient.close();
    }
}
