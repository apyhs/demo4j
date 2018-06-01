package com.github.kahlkn.demo4j.common.jdk;

import org.junit.Test;

import java.net.URI;
import java.net.URL;

public class UrlDemo {

    @Test
    public void test1() throws Exception {
        URL url = new URL("https://www.baidu.com/search?aaa=123");
        System.out.println(url.getHost());
        System.out.println(url.getProtocol());
        System.out.println(url.toString());
        URL url1 = new URL("www.baidu.com/search?aaa=123");
        System.out.println(url1.getHost());
        System.out.println(url1.getProtocol());
        System.out.println(url1.toString());
    }

    @Test
    public void test2() throws Exception {
        URI uri = URI.create("https://www.baidu.com/search?aaa=123");
        System.out.println(uri.getHost());
        System.out.println(uri.getPath());
        System.out.println(uri.getQuery());
        System.out.println(uri.getRawQuery());
        System.out.println(uri.toString());
        URI uri1 = new URI("www.baidu.com/search?aaa=123");
        System.out.println(uri1.getHost());
        System.out.println(uri1.getPath());
        System.out.println(uri1.getQuery());
        System.out.println(uri1.getRawQuery());
        System.out.println(uri1.toString());
    }

}
