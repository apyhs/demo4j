package com.github.kahlkn.demo.jdk;

import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JdkLoggerDemo {

    @Test
    public void test1() {
        Logger logger = Logger.getLogger(JdkLoggerDemo.class.getName());
        logger.setLevel(Level.INFO);

        System.out.println(logger.isLoggable(Level.FINE));
        logger.fine("fine");
        logger.info("info");
    }


}
