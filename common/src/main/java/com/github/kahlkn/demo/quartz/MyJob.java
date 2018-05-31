package com.github.kahlkn.demo.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

public class MyJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			System.out.println("data key = " + entry.getKey() + ", value = " + entry.getValue());
		}
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println();
	}
}
