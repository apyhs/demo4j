package demo.question;

import java.util.LinkedList;

/**
 * 多生产多消费
 */
public class NProductorNConsumer {

    public static void main(String[] args) {
        Resources res = new Resources();
        new Thread(new Consumer(res)).start();
        new Thread(new Consumer(res)).start();
        new Thread(new Productor(res)).start();
        new Thread(new Productor(res)).start();

    }

    public static class Resources {
        private LinkedList<String> data = new LinkedList<>();
        private Object loc = new Object();

        public void produce(String s) {
            synchronized (loc) {
                data.addLast(s);
            }
        }

        public String consume() {
            synchronized (loc) {
                if (!data.isEmpty()) {
                    return data.removeFirst();
                }
                return null;
            }
        }

    }

    public static class Consumer implements Runnable {
        private Resources res;

        public Consumer(Resources res) {
            this.res = res;
        }

        @Override
        public void run() {
            for (;;) {
                System.out.println("消费者[" + Thread.currentThread().getName() + "]正在消费：" + res.consume());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static class Productor implements Runnable {
        private Resources res;

        public Productor(Resources res) {
            this.res = res;
        }

        @Override
        public void run() {
            for (;;) {
                String s = Math.random() + "";
                res.produce(s);
                System.out.println("生产者[" + Thread.currentThread().getName() + "]正在生产：" + s);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
