package demo.jdk;

import org.junit.Test;
import saber.jdk.StreamKit;
import saber.jdk.ThreadKit;

import java.io.*;

public class ProcessDemo {

    @Test
    public void test0() throws IOException {
        Process p = Runtime.getRuntime().exec("ping -t www.baidu.com");
        StreamKit.write(p.getInputStream(), System.out);
    }

    @Test
    public void test1() throws IOException {
        Process p = Runtime.getRuntime().exec("cmd.exe /c netstat -ano");
        String s = StreamKit.read(new InputStreamReader(p.getInputStream(), "gb2312"));
        System.out.println(s);
    }

    @Test
    public void test2() throws IOException {
        Process p = Runtime.getRuntime().exec("ifconfig -all");
        String s = StreamKit.read(new InputStreamReader(p.getInputStream(), "gb2312"));
        System.out.println(s);
    }

}

// cmd /c dir 是执行完dir命令后封闭命令窗口。?
// cmd /k dir 是执行完dir命令后不封闭命令窗口。?
// cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会封闭。?
// cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会封闭。?
