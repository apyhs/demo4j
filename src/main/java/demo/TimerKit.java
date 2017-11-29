package demo;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerKit {

    public static Timer newTimer() {
        return new Timer();
    }

    public static Timer newTimer(String timerName) {
        if (StrKit.isBlank(timerName)) {
            throw new IllegalArgumentException("TimerName is blank.");
        } return new Timer(timerName);
    }

    public static void schedule(Timer timer, TimerTask task, Date firstTime, long period) {
        if (timer == null || task == null) {
            throw new IllegalArgumentException("Timer or task is null.");
        } timer.schedule(task, firstTime, period);
    }

    public static void schedule(Timer timer, TimerTask task, long delay, long period) {
        if (timer == null || task == null) {
            throw new IllegalArgumentException("Timer or task is null.");
        } timer.schedule(task, delay, period);
    }

    public static void schedule(Timer timer, TimerTask task, Date time) {
        if (timer == null || task == null) {
            throw new IllegalArgumentException("Timer or task is null.");
        } timer.schedule(task, time);
    }

    public static void schedule(Timer timer, TimerTask task, long delay) {
        if (timer == null || task == null) {
            throw new IllegalArgumentException("Timer or task is null.");
        } timer.schedule(task, delay);
    }

    public static void invokeTask(TimerTask task) {
        if (task == null) {
            throw new IllegalArgumentException("Task is null.");
        } task.run();
    }

    public static void destroyTask(TimerTask task) {
        if (task == null) {
            throw new IllegalArgumentException("Task is null.");
        } task.cancel();
    }

    public static int cleanTimer(Timer timer) {
        return timer.purge();
    }

    public static void destroyTimer(Timer timer) {
        timer.cancel(); // tasks will null
        timer = null;
    }

}
