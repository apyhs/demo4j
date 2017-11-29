package demotest;

import demo.FileKit;
import demo.StreamKit;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class FileKitTest {

    @Test
    public void test1() throws IOException {
        InputStream in = FileKit.getClassPathInputStream("log4j.properties");
        BufferedReader reader = StreamKit.getBufferedReader(in);
        System.out.println( StreamKit.readAndClose(reader));
    }

    @Test
    public void test2() throws IOException {
        System.out.println(FileKit.make("D:\\data\\logs"));
    }

}
