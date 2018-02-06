package com.apyhs.demo.cron4j;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import org.junit.Test;

public class Cron4jDemo {

    @Test
    public void test1() throws InterruptedException {
        // cron4j 只能到 分，并且 对于 ? 貌似也不适用
        SchedulingPattern pattern = new SchedulingPattern("*/1 * * * *");
        Scheduler scheduler = new Scheduler();
        scheduler.schedule(pattern, new MyTask());
        scheduler.start();
        Thread.sleep(120000);
        scheduler.stop();
    }

    @Test
    public void test2() throws InterruptedException {
        Scheduler scheduler = new Scheduler();
        scheduler.schedule("*/1 * * * *", new MyTask());
        scheduler.setDaemon(true);
        scheduler.start();
        Thread.sleep(120000);
        scheduler.stop();
    }

}
