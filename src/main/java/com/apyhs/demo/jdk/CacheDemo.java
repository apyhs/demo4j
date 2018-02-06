package com.apyhs.demo.jdk;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CacheDemo {

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.addAll(list.subList(0, 4));
        list2.addAll(list.subList(4, 10));
    }

    @Test
    public void test2() throws Exception {
        File outputFile = new File("E:\\proxyCheck.txt");
        File proxyFile = new File("E:\\proxy.txt");
        BufferedReader br = new BufferedReader(new FileReader(proxyFile));
        String line;
        HashSet<InetSocketAddress> proxys = new HashSet<>();

        while ((line = br.readLine()) != null) {
            String[] arr = line.split("	");
            String proxyaddr = arr[0];
            int proxyport = Integer.parseInt(arr[1]);
            InetSocketAddress i = new InetSocketAddress(proxyaddr, proxyport);
            // proxies.put( i.address(), i );
            proxys.add(i);
        }
        br.close();


        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
        int count = 0;
        for (InetSocketAddress address : proxys) {
            try {
                Socket socket = new Socket();
                socket.connect(address, 1000);
                writer.write(address.getHostString() + ":" + address.getPort());
                writer.newLine();
                writer.flush();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println(++count);
        }
        writer.flush();
        writer.close();

    }

}
