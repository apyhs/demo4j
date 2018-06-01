package com.github.kahlkn.demo4j.common.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.util.*;

public class ScheduleMgrDemo {

    public static JobDetailImpl getJob(String jobName, Class<? extends Job> clazz) {
        // 设置一个任务
        JobDetailImpl job = new JobDetailImpl();
        job.setName(jobName); job.setJobClass(clazz);
        JobDataMap data = new JobDataMap();
        data.put("hello", "你好，世界！");
        job.setJobDataMap(data);
        return job;
    }

    public static CronTriggerImpl getTrigger(String triggerName, String cronExpression) throws ParseException {
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setName(triggerName);
        trigger.setStartTime(new Date(new Date().getTime() + 1000));
        trigger.setCronExpression(cronExpression);
        return trigger;
    }

    @Test
    public void startScheduleAndTestScheduleJob() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 添加调度信息
        // scheduler.scheduleJob(getJob("job1", MyJob.class), getTrigger("trigger1", "0/1 * * * * ? "));

        // 检查调度器是否启动
        System.out.println(scheduler.isStarted());

        // 启动调度器
        scheduler.start();

        // 测试在启动调度器后添加调度信息
        // 添加调度信息
        scheduler.scheduleJob(getJob("job1", MyJob.class), getTrigger("trigger1", "0/1 * * * * ? "));

        // 检查调度器是否启动
        System.out.println(scheduler.isStarted());

        Thread.sleep(2000);

        // 测试 添加多个调度信息会怎么样
        // 添加调度信息
        scheduler.scheduleJob(getJob("job2", MyJob1.class), getTrigger("trigger2", "0/2 * * * * ? "));

        Thread.sleep(4000);

        // 检查调度器是否结束
        System.out.println(scheduler.isShutdown());

        scheduler.shutdown();

        // 检查调度器是否结束
        System.out.println(scheduler.isShutdown());

    }

    @Test// 失败
    public void testAddOne() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // 启动调度器
        scheduler.start();

        JobDetailImpl job1 = getJob("job1", MyJob.class);

        CronTriggerImpl trigger1 = new CronTriggerImpl();
        trigger1.setName("trigger1");
        trigger1.setStartTime(new Date(new Date().getTime() + 1000));
        trigger1.setCronExpression("0/1 * * * * ? ");
        trigger1.setJobKey(job1.getKey());

        // 持久化？
        // 测试 触发器 中关联 jobKey 是否就可以执行成功
        scheduler.scheduleJob(trigger1);

        Thread.sleep(4000);
        scheduler.shutdown();
    }

    @Test
    public void testScheduleJobs() throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // 启动调度器
        scheduler.start();

        // 测试 添加多个的情况
        Map<JobDetail, Set<? extends Trigger>> triggersAndJobs = new HashMap<>();
        HashSet<Trigger> triggers1 = new HashSet<>();
        triggers1.add(getTrigger("t1", "0/5 * * * * ? "));
        triggers1.add(getTrigger("t2", "0/10 * * * * ? "));
        triggersAndJobs.put(getJob("job1", MyJob.class), triggers1);

        HashSet<Trigger> triggers2 = new HashSet<>();
        triggers2.add(getTrigger("tt1", "0/5 * * * * ? "));
        triggers2.add(getTrigger("tt2", "0/10 * * * * ? "));
        triggersAndJobs.put(getJob("job2", MyJob1.class), triggers2);

        scheduler.scheduleJobs(triggersAndJobs, true);

        Thread.sleep(10000);
        scheduler.shutdown();
    }

}
