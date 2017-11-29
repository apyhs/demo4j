package demotest;

import demo.FileKit;
import demo.HttpKit;
import demo.StreamKit;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HttpKitTest {

    @Test
    public void test() throws IOException {
        HttpURLConnection conn = HttpKit.send("https://github.com/ideawu/ssdb/archive/master.zip", "GET", null);
        InputStream in = conn.getInputStream();
        FileKit.create("D:\\master.zip");
        FileOutputStream out = FileKit.getFileOutputStream(new File("D:\\master.zip"));
        StreamKit.writeAndClose(in, out);
    }
}
