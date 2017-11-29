package demotest.base;

import org.junit.Test;
import demo.base.LogUtils;

public class LogUtilsTest {

    @Test
    public void test() {
        LogUtils.severe("Hello, World! ");
        LogUtils.warning("Hello, World! ");
        LogUtils.info("Hello, World! ");
        LogUtils.config("Hello, World! ");
        LogUtils.fine("Hello, World! ");
    }

}
