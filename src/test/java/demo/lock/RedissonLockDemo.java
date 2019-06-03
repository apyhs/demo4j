package demo.lock;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedissonLockDemo {
    private static Logger log = LoggerFactory.getLogger(RedissonLockDemo.class);
    private static RedissonClient redisson;

    static {
        Config config = new Config();
//        config.setTransportMode(TransportMode.EPOLL);
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379").setPassword("123456");
        redisson = Redisson.create(config);
    }

    @Test
    public void test1() {
        RLock lock = redisson.getLock("aaaaa-test");
        boolean tryLock = lock.tryLock();
        log.info("tryLock {}", tryLock);
        lock.unlock();
    }

}
