package demo.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jDemo {

    @Test
    public void testEnabled() {
        Logger log = LoggerFactory.getLogger(Slf4jDemo.class);
        System.out.println(log.isTraceEnabled());
        System.out.println(log.isDebugEnabled());
        System.out.println(log.isInfoEnabled());
        System.out.println(log.isWarnEnabled());
        System.out.println(log.isErrorEnabled());
    }

    @Test
    public void testUsing() {
        Logger log = LoggerFactory.getLogger(Slf4jDemo.class);
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }
}
