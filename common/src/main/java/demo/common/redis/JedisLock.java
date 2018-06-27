package demo.common.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;

public class JedisLock {

    private static JedisPool jedisPool;

    @Before
    public void initJedisPool() {
        String host = "127.0.0.1";
        Integer port = 6379;
        Integer timeout = 8000;
        String password = "password";
        Integer database = 0;
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
    }

    @Test
    public void testFunc() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(lock(jedis, "aaaa-test-lock_key", "112233", 100000));
        jedis.close();
    }

    @Test
    public void test1() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        Jedis jedis = jedisPool.getResource();
                        System.out.print(lock(jedis, "aaaa-test-lock_key", "112233", 100) + " ");
                        if (j % 5 == 0) {
                            System.out.println();
                        }
                        jedis.close();
                    }
                }
            }).start();
        }
        Thread.sleep(60000);
    }

    @Test
    public void test2() throws InterruptedException {
        String key = "redis-lock-key";
        String val = "20180315 2008";
        final Integer[] count = {50};
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (count[0] > 0) {
                        Jedis jedis = jedisPool.getResource();
                        boolean lock = lock(jedis, key, val, 2000);
                        if (lock) {
                            if (count[0] <= 0) { continue; }
                            count[0]--;
                            String msg = count[0] + " - ";
                            boolean unlock = false;
                            for (int j = 0; j < 10; j++) {
                                unlock = unlock(jedis, key, val);
                                if (unlock) {
                                    msg += unlock + "\n";
                                    break;
                                }
                            }
                            if (!unlock) {
                                msg += unlock + "\n";
                                count[0]++;
                            }
                            buffer.append(msg);
                        }
                        jedis.close();
                    }
                }
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(buffer);
    }

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean lock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        /*
         * @param key
         * @param value
         * @param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
         *          if it already exist. NX：当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
         * @param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
         * @param time expire time in the units of expx
         */
        String result = jedis.set(lockKey, requestId, "NX", "PX", expireTime);

        if ("OK".equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean unlock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        Long success = 1L;
        if (success.equals(result)) {
            return true;
        }
        return false;
    }

}
