package com.github.kahlkn.demo4j.function.server.proxy.http.bio;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;

public class Application {

    public static void main(String[] args) throws Exception {
        System.out.println(">> http-proxy-server-bio 0.0.1-beta1 <<");
        Options opts = new Options();
        opts.addOption("h", false, "帮助");
//        opts.addOption("t", true, "初始的线程池大小，默认为“4”");
        opts.addOption("p", true, "要监听的端口");
        CommandLineParser parser = new BasicParser();
        CommandLine cli = parser.parse(opts, args);
        if (cli.getOptions().length <= 0 || cli.hasOption('h')) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("java -jar http-proxy-server-bio.jar -p 1234", opts);
        } else {
            int threadNum = 4;
            int port;
            String portStr = cli.getOptionValue("p");
            if (StringUtils.isBlank(portStr)) {
                System.out.println("端口号不能为空！");
                return;
            }
            try {
                port = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                System.out.println("解析端口号“" + portStr + "”失败，请检查！");
                return;
            }

            exec(port, threadNum);
        }
    }

    public static void exec(int port, int threadNum) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        for (; ; ) {
            new SocketHandleThread(serverSocket.accept()).start();
        }
    }

}
