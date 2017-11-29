package demo.demo.jdk.timer;

import demo.common.Time;
import demo.common.ThreadUtils;

import java.util.Date;
import java.util.Timer;

/**
 * 任务调度管理器 Demo
 */
public class TimerDemo {

    public static void main(String[] args) {
        TimerDemo manager = TimerDemo.getInstance();
        //启动任务，会立即执行一次，2s时执行完毕，5s时第二次执行，7s时第二次执行完毕
        manager.startTask(new Date(), 5000);

        ThreadUtils.sleepQuietly(8000);

        //8s时，stop原任务，动态更改启动时间
        manager.stop();
        System.out.println("当前时间：" + Time.on().toString());

        System.out.println("修改原计划，5s后重新执行");
        //5s后再启动，即13s时再启动
        manager.startTask(Time.on().addSecond(5).getDate(), 5000);
    }

    private static TimerDemo taskManager = null;

    /**
     * 时间调度对象
     */
    private static Timer timer = new Timer();

    /**
     * 任务
     */
    private static TimerTaskBean task = null;

    private TimerDemo() { }

    static {
        taskManager = new TimerDemo();
    }

    public static TimerDemo getInstance(){
        if(taskManager==null){
            taskManager = new TimerDemo();
        }
        return taskManager;
    }

    @SuppressWarnings("deprecation")
    public void startTask(Date startTime, long period){

        System.out.println("设置启动时间: "+startTime.toLocaleString());
        //如果当前时间超过了设定时间，会立即执行一次
        task = new TimerTaskBean();
        timer.schedule(task, startTime,period);

    }

    /**
     * 重新启动
     */
    public void restart() {
        clean();
        startTask(new Date(), 5000);
    }

    /**
     * 清空timer
     */
    public void clean() {
        if(task != null){
            task.cancel();
        }
        timer.purge();
    }

    /**
     * 停止任务
     */
    public void stop(){
        System.out.println("--------任务正在停止---------");
        clean();
        System.out.println("---------任务已停止----------");
    }

}
