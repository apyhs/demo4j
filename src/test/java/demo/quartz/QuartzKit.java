package demo.quartz;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class QuartzKit {
	
	public static Scheduler newScheduler(){
		try {
			return StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Scheduler newScheduler(JobDetail jobDetail, Trigger trigger){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			return scheduler;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Scheduler newScheduler(JobDetail jobDetail
			, Set<? extends Trigger> triggersForJob, boolean replace){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(jobDetail, triggersForJob, replace);
			return scheduler;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Scheduler newScheduler(
			Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJobs(triggersAndJobs, replace);
			return scheduler;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Scheduler startNewScheduler(JobDetail jobDetail, Trigger trigger){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return scheduler;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Scheduler startNewScheduler(JobDetail jobDetail
			, Set<? extends Trigger> triggersForJob, boolean replace){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(jobDetail, triggersForJob, replace);
			scheduler.start();
			return scheduler;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Scheduler startNewScheduler(
			Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJobs(triggersAndJobs, replace);
			scheduler.start();
			return scheduler;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static JobDetailImpl newJobDetail(String name
			, Class<? extends Job> jobClass){
		JobDetailImpl job = new JobDetailImpl();
		job.setName(name);
		job.setJobClass(jobClass);
		return job;
	}
	
	public static JobDetailImpl newJobDetail(String name
			, JobDataMap jobDataMap, Class<? extends Job> jobClass){
		JobDetailImpl job = new JobDetailImpl();
		job.setName(name);
		job.setJobClass(jobClass);
		job.setJobDataMap(jobDataMap);
		return job;
	}
	
	public static SimpleTriggerImpl newSimpleTrigger(
			String name, Date startTime){
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setName(name);
		trigger.setStartTime(startTime);
		return trigger;
	}
	
	public static SimpleTriggerImpl newSimpleTrigger(String name
			, Date startTime, int repeatCount, long repeatInterval){
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setName(name);
		trigger.setRepeatCount(repeatCount);
		trigger.setRepeatInterval(repeatInterval);
		trigger.setStartTime(startTime);
		return trigger;
	}
	
	public static CronTriggerImpl newCronTrigger(String name
			, Date startTime, CronExpression cronExpression){
		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setName(name);
		trigger.setStartTime(startTime);
		trigger.setCronExpression(cronExpression);
		return trigger;
	}
	
	public static CronTriggerImpl newCronTrigger(
			String name, Date startTime, String cronString){
		try {
			CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setName(name);
			trigger.setStartTime(startTime);
			trigger.setCronExpression(cronString);
			return trigger;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static void shutdown(Scheduler... schedulers){
		try {
			if(schedulers != null && schedulers.length != 0){
				for (Scheduler scheduler : schedulers){
					scheduler.shutdown();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
