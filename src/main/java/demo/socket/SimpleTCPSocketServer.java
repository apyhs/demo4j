package demo.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于TCP协议的Socket通信，实现用户登录，服务端
 */
public class SimpleTCPSocketServer {

    public static void main(String[] args) throws IOException {
        // 1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(10086); // 1024-65535的某个端口
        System.out.println("服务器已经启动...");
        // 2、调用accept()方法开始监听，等待客户端的连接
        Socket socket = serverSocket.accept();
        System.out.println("获取到连接...");
        // 3、获取输入流，并读取客户端信息
        InputStream in = socket.getInputStream();
        byte[] buffer = new byte[8192];
        for (int len; (len = in.read(buffer)) != -1;) {
            System.out.println("我是服务器，客户端说：" + new String(buffer, 0, len));
        }
        socket.shutdownInput(); // 关闭输入流

        // 4、获取输出流，响应客户端的请求
        OutputStream out = socket.getOutputStream();
        System.out.println("开始响应内容...");
        byte[] data = "欢迎您！".getBytes();
        out.write(data);
        out.flush();
        socket.shutdownOutput(); // 关闭输出流

        // 5、关闭资源
        System.out.println("开始释放资源...");
        socket.close(); // 关闭当前 socket
        serverSocket.close(); // 关闭服务器
    }
}
