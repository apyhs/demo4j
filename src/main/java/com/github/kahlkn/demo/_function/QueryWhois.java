package com.github.kahlkn.demo._function;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class QueryWhois {

    @Test
    public void test() throws IOException {
        Socket socket = new Socket("whois.cnnic.net.cn",43);
        System.out.println("连接到服务器了...");
        // 2、获取输出流，向服务器端发送信息
        OutputStream out = socket.getOutputStream(); // 字节输出流
        byte[] data = ("aliyun.com\n").getBytes();
        out.write(data);
        out.flush();
        socket.shutdownOutput();
        System.out.println("数据发送完毕...");

        // 3、获取输入流，并读取服务器端的响应信息
        InputStream in = socket.getInputStream();
        System.out.println("准备读取响应...");
        byte[] buffer = new byte[8192];
        for (int len; (len = in.read(buffer)) != -1;) {
            System.out.println(new String(buffer, 0, len));
        }
        socket.shutdownInput();

        //4、关闭资源
        System.out.println("开始释放资源...");
        socket.close();
    }

}
