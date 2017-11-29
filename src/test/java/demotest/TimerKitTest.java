package demotest;

import demo.ThreadKit;
import demo.TimerKit;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerKitTest {
    public static class taskBean extends TimerTask {
        private String name;

        public taskBean(String taskName) {
            this.name = taskName;
        }

        @Override
        public void run() {
            System.out.println(ThreadKit.currentThreadName() + " ["+ name +"] " + ": run now!");
        }
    }

    @Test
    public void test1() {
        Timer timer = TimerKit.newTimer();
        TimerKit.schedule(timer, new taskBean("task1"), 1000);
        TimerKit.schedule(timer, new taskBean("task2"), 0, 1000);
        ThreadKit.sleep(4000);
        TimerKit.cleanTimer(timer);
    }
}
