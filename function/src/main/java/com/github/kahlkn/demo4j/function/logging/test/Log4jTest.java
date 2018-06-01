package com.github.kahlkn.demo4j.function.logging.test;

import com.github.kahlkn.demo4j.function.logging.Logger;
import com.github.kahlkn.demo4j.function.logging.LoggerFactory;
import com.github.kahlkn.demo4j.function.logging.support.Log4jLoggerAdapter;
import org.junit.Before;
import org.junit.Test;

public class Log4jTest {

    @Before
    public void init() {
        LoggerFactory.setLoggerAdapter(new Log4jLoggerAdapter());
    }

    @Test
    public void test1() {
        Logger log = LoggerFactory.getLogger(Log4jTest.class);
        log.info("Hello, World! ");
        log.info("Hello, World! ");
        log.error("Hello, World! ");
        log.error("Hello, World! ");
    }

}
