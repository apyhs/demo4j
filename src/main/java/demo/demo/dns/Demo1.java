package demo.demo.dns;

import org.junit.Test;

import java.net.InetAddress;

public class Demo1 {

    @Test
    public void test1() throws Exception {
        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
        for (InetAddress address : addresses) {
            System.out.println(address.getHostAddress());
        }
    }

    @Test
    public void test2() throws Exception {
        InetAddress[] addresses = InetAddress.getAllByName("8.8.8.8"); // ip or DNS name
        for (InetAddress address : addresses) {
            System.out.println(address.getHostName());
        }
    }

    @Test
    public void test3() throws Exception {
        // 如果启动了security manager，则永久缓存，但一般情况下大家是不会去启动security manager的。
        // 可以再程序里面设置不缓存，或者在启动参数里面设置
        // 设置ttl：在命令启动JVM的时候设置参数"-Dnetworkaddress.cache.ttl=60 -Dsun.net.inetaddr.ttl=60"
        java.security.Security.setProperty("networkaddress.cache.ttl" , "0");
    }

}
