package demo.common.other;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilsTest {

    @Test
    public void test1() throws IOException {
        // System.out.println(HttpClientUtils.get("http://www.baidu.com"));
        // System.out.println(HttpClientUtils.get("http://www.taobao.com"));
        System.out.println(HttpClientUtils.get("http://uux.me"));
    }

    @Test
    public void test2() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("aaaa", "123");
        map.put("bbbb", "456");
        System.out.println(HttpClientUtils.post("http://uux.me", map));
    }

}
