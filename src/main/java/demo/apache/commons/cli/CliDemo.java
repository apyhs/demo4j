package demo.apache.commons.cli;

import org.apache.commons.cli.*;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class CliDemo {

    @Test
    public void test1() throws ParseException {
        // for test
        String[] args0 = {"-h"};
        String[] args1 = {"-i", "192.168.1.1", "-p", "8443", "-t", "https"};

        // do test
//        simpleTest(args0);
        simpleTest(args1);
    }

    public static void simpleTest(String[] args) {
        Options opts = new Options();
        opts.addOption("h", false, "HELP_DESCRIPTION");
        opts.addOption("i", true, "HELP_IPADDRESS");
        opts.addOption("p", true, "HELP_PORT");
        opts.addOption("t", true, "HELP_PROTOCOL");
        CommandLineParser parser = new BasicParser();
//        CommandLineParser parser = new GnuParser ();
//        CommandLineParser parser = new PosixParser();
        CommandLine cl;
        try {
            cl = parser.parse(opts, args);
            if (cl.getOptions().length > 0) {
                if (cl.hasOption('h')) {
                    HelpFormatter hf = new HelpFormatter();
                    hf.printHelp("May Options", opts);
                } else {
                    String ip = cl.getOptionValue("i");
                    String port = cl.getOptionValue("p");
                    String protocol = cl.getOptionValue("t");
                    System.out.println(ip);
                    System.out.println(port);
                    System.out.println(protocol);
                }
            } else {
                System.err.println("ERROR_NOARGS");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws ParseException {
        Option help = new Option("h", "the command help");
        Option user = OptionBuilder.withArgName("type").hasArg().withDescription("target the search type").create("t");

        // 此处定义参数类似于 java 命令中的 -D<name>=<value>
        Option property = OptionBuilder.withArgName("property=value").hasArgs(2).withValueSeparator().withDescription
                ("search the objects which have the target property and value").create("D");
        Options opts = new Options();
        opts.addOption(help);
        opts.addOption(user);
        opts.addOption(property);

//        String[] args = {"--h"};
//        String[] args = {"-D", "192.168.1.1", "-type", "https"};
//        String[] args = {"-D", "192.168.1.1", "-t", "https"};
        String[] args = {"-D", "aaa=bbbb", "-t", "https"};
        CommandLineParser parser = new BasicParser();
        CommandLine cl = parser.parse(opts, args);

        if (cl.hasOption('h')) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("May Options", opts);
        } else {
            System.out.println(cl.getOptionValue("t"));
            System.out.println(cl.getOptionValue("type"));
            System.out.println(cl.getOptionValue("D"));
            System.out.println(cl.getOptionValue("property=value"));
        }
    }

}
