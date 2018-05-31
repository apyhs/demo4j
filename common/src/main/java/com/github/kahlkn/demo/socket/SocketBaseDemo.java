package com.github.kahlkn.demo.socket;

import org.junit.Test;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class SocketBaseDemo {

    @Test
    public void test1() throws UnknownHostException {
        // 获取本机的InetAddress实例
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address.getHostName()); // 获取计算机名
        System.out.println(address.getHostAddress()); // 获取IP地址
        byte[] addressBytes = address.getAddress(); // 获取字节数组形式的IP地址,以点分隔的四部分
        System.out.println(Arrays.toString(addressBytes));

        // 获取其他主机的InetAddress实例
        InetAddress address1 = InetAddress.getByName("192.168.8.254");
        System.out.println(address1.getHostName()); // 获取计算机名
        System.out.println(address1.getHostAddress()); // 获取IP地址
        System.out.println(Arrays.toString(address1.getAddress()));
    }

    @Test
    public void test2() throws MalformedURLException {
        //创建一个URL的实例
        URL baidu =new URL("http://www.baidu.com/");
        URL url =new URL(baidu,"/index.html?username=tom#test"); // ？表示参数，#表示锚点
        System.out.println(url.getProtocol()); // 获取协议
        System.out.println(url.getHost());  // 获取主机
        System.out.println(url.getPort());  // 如果没有指定端口号，根据协议不同使用默认端口。此时getPort()方法的返回值为 -1
        System.out.println(url.getPath());  // 获取文件路径
        System.out.println(url.getFile());  // 文件名，包括文件路径+参数
        System.out.println(url.getRef());   // 相对路径，就是锚点，即#号后面的内容
        System.out.println(url.getQuery()); // 查询字符串，即参数
    }

    @Test
    public void test3() throws IOException {
        // 使用URL读取网页内容
        // 创建一个URL实例
        URL url =new URL("http://www.baidu.com");
        InputStream in = url.openStream(); // 通过openStream方法获取资源的字节输入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        String data = reader.readLine(); // 读取数据
        while (data != null) {
            System.out.println(data); // 输出数据
            data = reader.readLine();
        }
        reader.close();
    }

}
