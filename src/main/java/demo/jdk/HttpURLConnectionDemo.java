package demo.jdk;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpURLConnectionDemo {

    @Test
    public void test1() throws Exception{
        URL url = new URL("https://my.oschina.net/jfinal/blog");
//		URL url = new URL("http://www.baidu.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        System.out.println(IOUtils.toString(inputStream, Charset.forName("utf-8")));
    }

    @Test
    public void test2() throws Exception{
        URL url = new URL("http://ip.chinaz.com/getip.aspx");
//		URL url = new URL("http://www.baidu.com");

        // 创建代理服务器
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1",5000);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 以下三行是在需要验证时，输入帐号密码信息
        // 帐号密码用:隔开，base64加密方式
//        String proxyUser = "";
//        String proxyPass = "";
//        String headerValue = "Basic " + Base64.encodeBase64String(((proxyUser + ":" + proxyPass).getBytes()));
//        connection.setRequestProperty("Proxy-Authorization", headerValue);

        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        System.out.println(IOUtils.toString(inputStream, Charset.forName("utf-8")));
    }

}
