package com.github.kahlkn.demo.thread;

import com.github.kahlkn.artoria.util.ThreadUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

public class Demo {
    private ExecutorService pool;

    @Before
    public void init() {
        // Executors.newFixedThreadPool(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        pool = new ThreadPoolExecutor(6, 10, 0L
                , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
    }

    @After
    public void destroy() {
        pool.shutdown();
    }

    @Test
    public void test1() throws Exception {
        Future<Object> callable = pool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("Callable and return now time...");
                return System.currentTimeMillis();
            }
        });
        System.out.println(callable.get());

        Future<?> runnable = pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable and not return...");
            }
        });
        System.out.println(runnable.get());

        Object result = "Hello, World! ";
        Future<Object> runnableWithRes = pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable with result and not return...");
            }
        }, result);
        System.out.println(runnableWithRes.get());
    }

    @Test
    public void test2() throws Exception {
        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 4; j++) {
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            });
        }
        ThreadUtils.sleepQuietly(5000);
    }

}
