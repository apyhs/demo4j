package demo.quartz;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Map<?, ?> data = context.getJobDetail().getJobDataMap();
		System.out.println("data is " + data.get("1"));
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println("MyJob do it now!");
		System.out.println();
	}
}
