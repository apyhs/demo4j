package demotest.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import demo.jdk.CodeKit;

public class TestTask implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        System.out.println("TestTask run... [data : " + CodeKit.encodeMap(dataMap) + "]");
    }

}
