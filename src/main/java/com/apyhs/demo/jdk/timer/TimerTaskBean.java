package com.apyhs.demo.jdk.timer;


import com.apyhs.artoria.time.DateTime;
import com.apyhs.artoria.util.ThreadUtils;

public class TimerTaskBean extends java.util.TimerTask {

    private String msg;

    public TimerTaskBean() {
        this.msg = "Hello, World!";
    }

    public TimerTaskBean(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println("-----------------------Task[" + Thread.currentThread().getName() + "] Start-----------------------");
        for(int i=0;i<2;i++){
            System.out.println(msg);
            ThreadUtils.sleepQuietly(1000);
            System.out.println("已执行【"+(i+1)+"】秒钟，at: " + DateTime.create());
        }
        System.out.println("本次任务调度结束，at: " + DateTime.create());
        System.out.println("-----------------------Task[" + Thread.currentThread().getName() + "] End-----------------------");
    }
}
