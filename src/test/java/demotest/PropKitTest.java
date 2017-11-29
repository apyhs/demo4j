package demotest;

import demo.PathKit;
import demo.PropKit;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

public class PropKitTest {

    @Test
    public void test1() throws Exception {
        File path = PathKit.classPath();
        System.out.println(path);
        File[] files = path.listFiles();
        if(files != null && files.length != 0){
            for (File file : files)
                System.out.println(file);
        }
        Properties prop = PropKit.load("log4j.properties");
        // 所以 FileInputStream 对于相对路径是没效果的
        System.out.println(prop);
    }

    @Test
    public void test2() {
        Properties prop = PropKit.use("log4j.properties");
        System.out.println(PropKit.get("log4j.properties", "log4j.appender.file"));
        System.out.println(PropKit.get("log4j.properties", "log4j.appender.stdout"));
    }

    @Test
    public void test3() {
        System.out.println((Integer)null);
        System.out.println((Long)null);
        System.out.println((Boolean)null);
    }
    
}
