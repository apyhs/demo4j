package demo.jdk;


import saber.jdk.ThreadKit;
import saber.jdk.TimeKit;

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
        System.out.println("-----------------------Task[" + ThreadKit.threadName() + "] Start-----------------------");
        for(int i=0;i<2;i++){
            System.out.println(msg);
            ThreadKit.fastSleep(1000);
            System.out.println("已执行【"+(i+1)+"】秒钟，at: " + TimeKit.format());
        }
        System.out.println("本次任务调度结束，at: " + TimeKit.format());
        System.out.println("-----------------------Task[" + ThreadKit.threadName() + "] End-----------------------");
    }
}
