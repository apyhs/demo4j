package demotest.base;

import org.junit.Test;
import demo.base.HttpUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class HttpUtilsTest {

    @Test
    public void test1() throws IOException, GeneralSecurityException {
        System.out.println(HttpUtils.get("http://uux.me"));
    }

}
