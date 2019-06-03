package demo.lock;

import artoria.util.ThreadUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class RedisLockerTest {
    private static Logger log = LoggerFactory.getLogger(RedissonLockDemo.class);
    private static RedisLocker redisLocker;
    private ExecutorService pool;
    private Integer num = 100;

    static {
        Config config = new Config();
//        config.setTransportMode(TransportMode.EPOLL);
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379").setPassword("123456");
        RedissonClient redisson = Redisson.create(config);
        redisLocker = new RedisLocker(redisson);
    }

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
                            redisLocker.lock("redis-locker-test1");
                            try {
                                if (num >= 0) {
                                    log.info("{}", num--);
                                }
                            }
                            finally {
                                redisLocker.unlock("redis-locker-test1");
                            }
                        }
                    }
                }
            });
        }
        ThreadUtils.sleepQuietly(10000);
    }

}
