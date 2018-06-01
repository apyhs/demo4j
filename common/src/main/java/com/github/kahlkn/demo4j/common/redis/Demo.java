package com.github.kahlkn.demo4j.common.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Demo {

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
    public void test1() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.ping());
        for (int i = 0; i < 10; i++) {
            jedis.set(i + "", "v" + i);
        }
        System.out.println(jedis.get("5"));
        System.out.println(jedis.dbSize());
        jedis.close();
    }

    @Test
    public void test4() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.ping());
        System.out.println(jedis.select(1));
        for (int i = 0; i < 10; i++) {
            jedis.set(i + "", "v" + i);
        }
        System.out.println(jedis.dbSize());
        jedis.close();
    }

    @Test
    public void test2() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.ping());
        for (int i = 0; i < 100; i++) {
            jedis.del(i + "");
        }
        System.out.println(jedis.dbSize());
        jedis.close();
    }

    @Test
    public void test3() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.ping());
        System.out.println(jedis.flushAll());
        System.out.println(jedis.select(0));
        System.out.println(jedis.dbSize());
        System.out.println(jedis.select(1));
        System.out.println(jedis.dbSize());
        jedis.close();
    }

    @Test
    public void test5() {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.ping());
        System.out.println(jedis.flushDB());
        System.out.println(jedis.select(0));
        System.out.println(jedis.dbSize());
        System.out.println(jedis.select(1));
        System.out.println(jedis.dbSize());
        jedis.close();
    }

}
