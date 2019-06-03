package demo.lock;

import artoria.util.ThreadUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    private static Logger log = LoggerFactory.getLogger(ReentrantLockTest.class);
    private ReentrantLock lock = new ReentrantLock();
    private ExecutorService pool;
    private Integer num = 100;

    @Before
    public void init() {
        // Executors.newFixedThreadPool(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        pool = new ThreadPoolExecutor(10, 10, 0L
                , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
    }

    @After
    public void destroy() {

        pool.shutdown();
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000000; j++) {
                        if (num >= 0) {
                            lock.lock();
                            try {
                                if (num >= 0) {
                                    log.info("{}", num--);
                                }
                            }
                            finally {
                                lock.unlock();
                            }
                        }
                    }
                }
            });
        }
        ThreadUtils.sleepQuietly(10000);
    }

}
