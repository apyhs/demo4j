package demo.jdk.logging;

import org.junit.Test;

import java.util.logging.Logger;

public class JdkLoggerPerformance {
    private static final Integer count = 20;
    private static final Integer loopCount = 100000;

    @Test
    public void test1() {
        Logger log = Logger.getLogger(JdkLoggerPerformance.class.getName());
        System.out.print("Jdk Logger: ");
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < loopCount; j++) {
                log.finer("hello");
            }
            long end = System.currentTimeMillis();
            System.out.print((end - start) + " ");
        }
        System.out.println();
    }

}
