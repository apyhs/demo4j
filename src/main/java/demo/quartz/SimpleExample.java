package demo.quartz;

import java.util.Date;

import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import kit4j.TimeKit;

public class SimpleExample {
	
	@Test
	public void run() throws Exception {
		System.out.println("------- 初始化 ----------------------");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
 
        System.out.println("------- 计划任务 -------------------");
        Date runTime = new Date(TimeKit.ts0() + 10000); 
 
        // define the job and tie it to our HelloJob class
//		JobDetail job = new JobDetailImpl("job1", "group1", MyJob.class);
		JobDetailImpl job = new JobDetailImpl();
		job.setName("job1");
		job.setGroup("group1");
		job.setJobClass(MyJob.class);
		JobDataMap data = new JobDataMap();
		data.put("1", "你好，世界！");
		job.setJobDataMap(data);
 
        // Trigger the job to run on the next round minute
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setStartTime(runTime);
        trigger.setName("trigger1");
        trigger.setJobGroup("group1");
        
 
        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
        System.out.println(job.getName() + " will run at: " + runTime);
 
        scheduler.start();
        System.out.println("------- Started Scheduler -----------------");
 
        Thread.sleep(60000);
        
        // shutdown the scheduler
        System.out.println("------- Shutting Down ---------------------");
        scheduler.shutdown(true);
        System.out.println("------- Shutdown Complete -----------------");
	}
}
