package demo.demo.jdk.timer;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;

public class TimerDemo1 {

    @Test
    public void test1() throws InterruptedException {
        // 创建任务对象
        TimerTaskBean task = new TimerTaskBean();
        // 创建调度对象
        Timer timer = new Timer();

        // 启动调度对象
        // 如果当前时间超过了设定时间，会立即执行一次
        Date startTime = new Date();
        long period = 2000; // 间隔，毫秒
        timer.schedule(task, startTime, period);

        // 让主线程飞一会
        Thread.sleep(5000);

        // 取消任务
        task.cancel();
        // 测试取消之后是否为null
        System.out.println(task);// 并不是null

        // 释放 调度对象 资源
        timer.purge();
        // 测试调度对象是否为null
        System.out.println(timer);// 并不是null
        // 测试任务是否为null
        System.out.println("task>>" + task);// 并不是null

        // 启动调度对象
        timer.schedule(new TimerTaskBean("123"), new Date(), 1000);

        // 让主线程飞一会
        Thread.sleep(5000);

        // 取消调度对象
        timer.cancel();
        // 测试调度对象是否为null
        System.out.println(timer);
    }

    @Test
    public void test2() throws InterruptedException {
        // 创建任务对象
        TimerTaskBean task = new TimerTaskBean();
        TimerTaskBean task1 = new TimerTaskBean("123");
        // 创建调度对象
        Timer timer = new Timer();

        // 启动调度对象
        timer.schedule(task, new Date(), 2000);
        timer.schedule(task1, new Date(), 2000);

        // 让主线程飞一会
        Thread.sleep(5000);

        // 取消任务
        task.cancel();

        // 一个 Timer 对象相当于一个线程
        // 一个线程内，可以有多个任务依次执行

        // 让主线程飞一会
        Thread.sleep(10000);
    }

    @Test
    public void test3() throws InterruptedException {
        // 创建调度对象
        Timer timer = new Timer();
        Timer timer1 = new Timer();

        // 启动调度对象
        timer.schedule(new TimerTaskBean(), new Date(), 2000);
        timer1.schedule(new TimerTaskBean("123"), new Date(), 2000);

        // 让主线程飞一会
        Thread.sleep(5000);

        // 取消调度对象，会停止所有任务
        timer.cancel();
        timer1.cancel();

        // 尝试再次启动新的调度任务，调度对象的线程已经结束
        // timer.schedule(new TimerTaskBean(), new Date(), 2000);

        // 让主线程飞一会
        Thread.sleep(10000);
    }

}
