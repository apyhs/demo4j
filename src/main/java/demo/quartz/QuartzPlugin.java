package demo.quartz;

import demo.jdk.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class QuartzPlugin{
    private static final String defaultJobsFileName = "quartz-jobs.properties";
    private static boolean hasDefault = false;
    private String pluginName;
    private String jobsFileName;
    private SchedulerFactory schedulerFactory;

    public String getJobsFileName() {
        return jobsFileName;
    }

    public void setJobsFileName(String jobsFileName) {
        this.jobsFileName = jobsFileName;
    }

    public SchedulerFactory getSchedulerFactory() {
        return schedulerFactory;
    }

    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    public QuartzPlugin() {
        if (hasDefault) {
            throw new IllegalArgumentException("Already exist default, please give plugin name. ");
        }
        this.pluginName = SchedulerMgr.defaultPluginName;
        hasDefault = true;
    }

    public QuartzPlugin(String pluginName) {
        this.pluginName = pluginName;
    }

    public boolean start() {
        LogKit.info("QuartzPlugin[" + pluginName + "]: Try to init plugin ...");
        if (StrKit.isBlank(jobsFileName)) {
            jobsFileName = defaultJobsFileName;
        }
        if (schedulerFactory == null) {
            schedulerFactory = new StdSchedulerFactory();
        }
        Scheduler defScheduler = SchedulerMgr.put(pluginName, schedulerFactory);
        try {
            LogKit.info("QuartzPlugin[" + pluginName + "]: Try to load \"" + jobsFileName + "\" ...");
            Properties jobsProp = PropKit.use(jobsFileName);
            Enumeration<Object> e = jobsProp.keys();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                if (key.indexOf("job.name") == 0) {
                    String jobName = jobsProp.getProperty(key);
                    if (StrKit.isBlank(jobName)) continue;
                    String className = jobsProp.getProperty(jobName + ".class");
                    if (StrKit.isBlank(className)) continue;
                    String triggerName = jobsProp.getProperty(jobName + ".trigger");
                    if (StrKit.isBlank(triggerName)) continue;
                    String cron = jobsProp.getProperty(jobName + ".cron");
                    if (StrKit.isBlank(cron)) continue;
                    String groupName = jobsProp.getProperty(jobName + ".group");
                    String data = jobsProp.getProperty(jobName + ".data");
                    String startTime = jobsProp.getProperty(jobName + ".start");

                    @SuppressWarnings("unchecked")
                    Class<? extends Job> jobClass =
                            (Class<? extends Job>) Class.forName(className);

                    JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
                    if (StrKit.notBlank(groupName))
                        jobBuilder.withIdentity(jobName, groupName);
                    else jobBuilder.withIdentity(jobName);
                    if (StrKit.notBlank(data)) {
                        Map<String, String> map = CodeKit.decodeMap(data);
                        if (map.size() > 0) {
                            JobDataMap jobDataMap = new JobDataMap();
                            jobDataMap.putAll(map);
                            jobBuilder.setJobData(jobDataMap);
                        }
                    }

                    TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                            .withSchedule(CronScheduleBuilder.cronSchedule(cron));
                    if (StrKit.notBlank(groupName))
                        triggerBuilder.withIdentity(triggerName, groupName);
                    else triggerBuilder.withIdentity(triggerName);
                    if (StrKit.notBlank(startTime)) {
                        triggerBuilder.startAt(TimeKit.parse(startTime));
                    }

                    JobDetail job = jobBuilder.build();
                    CronTrigger trigger = triggerBuilder.build();
                    defScheduler.scheduleJob(job, trigger);
                    LogKit.info("QuartzPlugin[" + pluginName + "]: Init job \"" + jobName + "\" success. ");
                }
            }
            LogKit.info("QuartzPlugin[" + pluginName + "]: Try to start default scheduler ...");
            defScheduler.start();
            LogKit.info("QuartzPlugin[" + pluginName + "]: Default scheduler start success. ");
            return true;
        } catch (Exception e) {
            LogKit.error("QuartzPlugin[" + pluginName + "]: Init plugin \"" + pluginName + "\" failure! ");
            throw new RuntimeException(e);
        }
    }

    public boolean stop() {
        try {
            LogKit.info("QuartzPlugin[" + pluginName + "]: Try to stop schedulers ...");
            Set<String> names = SchedulerMgr.schedulerNames(pluginName);
            if (names.size() > 0) {
                for (String name : names) {
                    Scheduler scheduler = SchedulerMgr.use(pluginName, name);
                    scheduler.shutdown(true);
                    int exeNum = scheduler.getMetaData().getNumberOfJobsExecuted();
                    LogKit.info("QuartzPlugin[" + pluginName + "]: Scheduler \"" + name + "\" executed " + exeNum + " jobs.");
                }
            }
            return true;
        } catch (Exception e) {
            LogKit.error("QuartzPlugin[" + pluginName + "]: Try to stop schedulers failure! ");
            throw new RuntimeException(e);
        }
    }

}
