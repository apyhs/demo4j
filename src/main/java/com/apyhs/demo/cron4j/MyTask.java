package com.apyhs.demo.cron4j;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.util.Date;

public class MyTask extends Task {

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        System.out.println("run + 1 " + new Date().toString());
    }

}
