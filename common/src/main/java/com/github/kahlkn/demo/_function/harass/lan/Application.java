package com.github.kahlkn.demo._function.harass.lan;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Application {

    public static void main(String[] args) throws Exception {
        System.out.println(">> harass-lan 0.0.1-beta1 <<");
        Options opts = new Options();
        opts.addOption("h", false, "帮助");
        opts.addOption("p", true, "去干扰的端口列表，英文逗号分隔");
        opts.addOption("t", true, "开多少线程来跑");
        CommandLineParser parser = new BasicParser();
        CommandLine cli = parser.parse(opts, args);
        if (cli.getOptions().length <= 0 || cli.hasOption('h')) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("java -jar harass-lan.jar -p 80,81,82 -t 10", opts);
        } else {
            Integer[] ports;
            int threadNum;
            String portsStr = cli.getOptionValue("p");
            String threadsStr = cli.getOptionValue("t");

            if (StringUtils.isBlank(portsStr)) {
                System.out.println("端口列表是空的！");
                return;
            }
            String[] split = portsStr.split(",");
            if (ArrayUtils.isEmpty(split)) {
                System.out.println("解析端口列表后未发现有效数据！");
                return;
            } else {
                ports = new Integer[split.length];
                int errorCount = 0;
                for (int i = 0; i < ports.length; i++) {
                    try {
                        ports[i] = Integer.valueOf(split[i]);
                    } catch (NumberFormatException e) {
                        System.out.println("端口值\"" + split[i] + "\"有误，将排除这个值！");
                        errorCount++;
                    }
                }
                if (errorCount == ports.length) {
                    System.out.println("解析端口列表后发现都为无效值！");
                    return;
                }
            }

            if (StringUtils.isBlank(threadsStr)) {
                System.out.println("线程数是空的！");
                return;
            }
            try {
                threadNum = Integer.valueOf(threadsStr);
            } catch (NumberFormatException e) {
                System.out.println("线程数\"" + threadsStr + "\"有误！");
                return;
            }

            HarassLan.exec(ports, threadNum);
        }
    }

}
