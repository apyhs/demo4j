package demotest;

import demo.LogKit;
import org.junit.Test;

public class LogKitTest {

    @Test
    public void test1() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            LogKit.debug("你好！你好！你好！你好！", e);
            LogKit.info("你好！你好！你好！你好！", e);
            LogKit.warn("你好！你好！你好！你好！", e);
            LogKit.error("你好！你好！你好！你好！", e);
        }
    }

}
