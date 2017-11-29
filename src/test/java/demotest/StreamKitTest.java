package demotest;

import demo.StreamKit;
import org.junit.Test;

import java.io.*;

public class StreamKitTest {

    @Test
    public void test1() throws Exception {
        File file = new File("C:\\Users\\admin\\Desktop\\测试.txt");
        File fcopy = new File("C:\\Users\\admin\\Desktop\\测试_COPY.txt");
        fcopy.createNewFile();
        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream(fcopy);
        StreamKit.writeAndClose(in, out);
    }

    @Test
    public void test2() throws Exception {
        FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\测试.txt");
        System.out.println(StreamKit.readAndClose(StreamKit.getBufferedReader(in)));
    }

    @Test
    public void test3() throws Exception {
        InputStreamReader in = new InputStreamReader(new FileInputStream("C:\\Users\\admin\\Desktop\\测试.txt"), "utf-8");
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("C:\\Users\\admin\\Desktop\\测试_COPY.txt"), "utf-8");
        StreamKit.writeAndClose(in, out);
    }

}
