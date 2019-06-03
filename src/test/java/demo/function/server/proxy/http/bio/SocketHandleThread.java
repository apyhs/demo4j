package demo.function.server.proxy.http.bio;

import artoria.io.IOUtils;

import java.io.*;
import java.net.Socket;

public class SocketHandleThread extends Thread {
    private Socket socket;

    public SocketHandleThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream clientOutput = null;
        InputStream clientInput = null;
        Socket proxySocket = null;
        InputStream proxyInput = null;
        OutputStream proxyOutput = null;
        try {
            clientInput = socket.getInputStream();
            clientOutput = socket.getOutputStream();
            String line;
            String host = "";
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientInput));
            StringBuilder headStr = new StringBuilder();
            // 读取HTTP请求头，并拿到HOST请求头和method
            System.out.println(">>>>>>>>>>>>>>>>");
            while (null != (line = clientReader.readLine())) {
                System.out.println(line);
                headStr.append(line).append("\r\n");
                if (line.length() == 0) {
                    break;
                } else {
                    String[] temp = line.split(" ");
                    if (temp[0].contains("Host")) {
                        host = temp[1];
                    }
                }
            }
            System.out.println("<<<<<<<<<<<<<<<<");
            System.out.println();
            String type = headStr.substring(0, headStr.indexOf(" "));
            // 根据host头解析出目标服务器的host和port
            String[] hostTemp = host.split(":");
            host = hostTemp[0];
            int port = 80;
            if (hostTemp.length > 1) {
                port = Integer.valueOf(hostTemp[1]);
            }
            // 连接到目标服务器
            proxySocket = new Socket(host, port);
            proxyInput = proxySocket.getInputStream();
            proxyOutput = proxySocket.getOutputStream();
            //根据HTTP method来判断是https还是http请求
            if ("CONNECT".equalsIgnoreCase(type)) {
                // https先建立隧道
                clientOutput.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                clientOutput.flush();
            } else {
                // http直接将请求头转发
                proxyOutput.write(headStr.toString().getBytes());
            }

            // 转发客户端请求至目标服务器
            byte[] buf = new byte[8192];
            proxyOutput.write(headStr.toString().getBytes());
            // 转发目标服务器响应至客户端
            for (int len; -1 != (len = proxyInput.read(buf)); ) {
                clientOutput.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(proxyInput);
            IOUtils.closeQuietly(proxyOutput);
            IOUtils.closeQuietly(proxySocket);
            IOUtils.closeQuietly(clientInput);
            IOUtils.closeQuietly(clientOutput);
            IOUtils.closeQuietly(socket);
        }
    }

}
