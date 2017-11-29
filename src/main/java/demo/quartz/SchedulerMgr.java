package demo.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SchedulerMgr {
    private static final ConcurrentHashMap<String, SchedulerWrap> cache = new ConcurrentHashMap<>();
    private static final String defaultSchedulerName = "_default_scheduler_name";
    static final String defaultPluginName = "_default_plugin_name";

    public static Scheduler use() {
        return use(defaultPluginName, defaultSchedulerName);
    }

    public static Scheduler use(String schedulerName) {
        return use(defaultPluginName, schedulerName);
    }

    public static Scheduler use(String pluginName, String schedulerName) {
        return get(pluginName).get(schedulerName);
    }

    public static Set<String> schedulerNames() {
        return get(defaultPluginName).names();
    }

    public static Set<String> schedulerNames(String pluginName) {
        return get(pluginName).names();
    }

    static Scheduler put(String pluginName, SchedulerFactory schedulerFactory) {
        SchedulerWrap schedulerWrap = new SchedulerWrap(schedulerFactory);
        cache.put(pluginName, schedulerWrap);
        return schedulerWrap.get(defaultSchedulerName);
    }

    static SchedulerWrap get(String pluginName) {
        SchedulerWrap result = cache.get(pluginName);
        if (result == null) {
            throw new IllegalArgumentException("Not found \"" + pluginName + "\". ");
        }
        return result;
    }

    private static class SchedulerWrap {
        private final ConcurrentHashMap<String, Scheduler> schedulers = new ConcurrentHashMap<>();
        private SchedulerFactory schedulerFactory = null;

        SchedulerWrap(SchedulerFactory schedulerFactory) {
            this.schedulerFactory = schedulerFactory;
        }

        Scheduler get(String schedulerName) {
            Scheduler result = schedulers.get(schedulerName);
            if (result == null) {
                try {
                    result = schedulerFactory.getScheduler();
                    schedulers.put(schedulerName, result);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return result;
        }

        Set<String> names() {
            return schedulers.keySet();
        }

    }

}
