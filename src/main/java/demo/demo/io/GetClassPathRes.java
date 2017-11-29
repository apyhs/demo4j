package demo.demo.io;

import org.junit.Test;

import java.io.InputStream;

public class GetClassPathRes {

    @Test
    public void test2() {
        String fileName = "";
        InputStream in = GetClassPathRes.class.getClassLoader().getResourceAsStream(fileName);
    }

}
