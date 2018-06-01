package com.github.kahlkn.demo4j.function.logging.test;

import com.github.kahlkn.artoria.exception.UncheckedException;
import com.github.kahlkn.demo4j.function.logging.Level;
import com.github.kahlkn.demo4j.function.logging.Logger;
import com.github.kahlkn.demo4j.function.logging.LoggerFactory;
import org.junit.Test;

public class LoggerTest {
    private static Logger log = LoggerFactory.getLogger(LoggerTest.class.getName());

    @Test
    public void logMessage() {
        System.err.println("Test logger message, and show \"using logger: artoria.logging.JdkLoggerAdapter\".");
        log.trace("Hello, World!");
        log.debug("Hello, World!");
        log.info("Hello, World!");
        log.warn("Hello, World!");
        log.error("Hello, World!");
        System.err.println("set level INFO");
        LoggerFactory.setLevel(Level.INFO);
        log.trace("Hello, World!");
        log.debug("Hello, World!");
        log.info("Hello, World!");
        log.warn("Hello, World!");
        log.error("Hello, World!");
    }

    @Test
    public void logException() {
        System.err.println("Test logger RuntimeException, and show \"using logger: com.apyhs.artoria.logging.JdkLoggerAdapter\".");
        log.trace("Hello, World!", new UncheckedException());
        log.debug("Hello, World!", new UncheckedException());
        log.info("Hello, World!", new UncheckedException());
        log.warn("Hello, World!", new UncheckedException());
        log.error("Hello, World!", new UncheckedException());
    }

}
