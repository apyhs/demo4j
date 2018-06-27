package demo.function.harass.site;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

public class Application {

    public static void main(String[] args) throws Exception {
        System.out.println(">> harass-site 0.0.1-beta1 <<");
        Options opts = new Options();
        opts.addOption("h", false, "帮助");
        opts.addOption("t", true, "开多少线程来跑，默认值为“4”");
        opts.addOption("m", true, "Http请求的方法，默认值为“GET”");
        opts.addOption("u", true, "Http请求的URL地址，必填");
        opts.addOption("d", true, "Http请求比如POST等要发送的DATA，可选");
        CommandLineParser parser = new BasicParser();
        CommandLine cli = parser.parse(opts, args);
        if (cli.getOptions().length <= 0 || cli.hasOption('h')) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("java -jar harass-site.jar -t 4 -m GET -u http://url.com", opts);
        } else {
            int threadNum = 4;
            String method = cli.getOptionValue("m");
            String url = cli.getOptionValue("u");
            String data = cli.getOptionValue("d");

            String threadNumStr = cli.getOptionValue("t");
            if (StringUtils.isNotBlank(threadNumStr)) {
                try {
                    threadNum = Integer.parseInt(threadNumStr);
                    if (threadNum == 0) {
                        System.out.println("线程数不能为“0”，将使用默认值“4”");
                        threadNum = 4;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("解析线程数失败，将使用默认值“4”");
                    threadNum = 4;
                }
            }
            if (StringUtils.isBlank(method)) method = "GET";
            if (StringUtils.isBlank(url)) {
                System.out.println("URL地址不能为空！");
                return;
            }
            // TODO
            // HarassSite.exec(threadNum, method, url, data);
        }
    }

}
