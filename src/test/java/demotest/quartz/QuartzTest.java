package demotest.quartz;

import org.junit.Test;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import demo.jdk.ThreadKit;
import demo.quartz.QuartzPlugin;
import demo.quartz.SchedulerMgr;

public class QuartzTest {

    @Test
    public void test1() {
        QuartzPlugin quartzPlugin = new QuartzPlugin();
        quartzPlugin.start();
        ThreadKit.fastSleep(60000);
        quartzPlugin.stop();
    }

    @Test
    public void test2() throws SchedulerException {
        QuartzPlugin quartzPlugin = new QuartzPlugin();
        quartzPlugin.start();
        ThreadKit.fastSleep(10000);
        SchedulerMgr.use().pauseJob(new JobKey("testTask"));
        ThreadKit.fastSleep(10000);
        SchedulerMgr.use().resumeJob(new JobKey("testTask"));
        ThreadKit.fastSleep(10000);
        quartzPlugin.stop();
    }

    @Test
    public void test3() throws SchedulerException {
        QuartzPlugin quartzPlugin = new QuartzPlugin();
        quartzPlugin.start();
        QuartzPlugin quartzPlugin1 = new QuartzPlugin("quartzPlugin1");
        quartzPlugin1.setJobsFileName("quartz-jobs1.properties");
        quartzPlugin1.start();
        ThreadKit.fastSleep(5000);
        quartzPlugin.stop();
        quartzPlugin1.stop();
    }

    @Test
    public void test4() throws SchedulerException {
        QuartzPlugin quartzPlugin = new QuartzPlugin("test");
        quartzPlugin.start();
        SchedulerMgr.use("test", "123");
        quartzPlugin.stop();
    }

}
