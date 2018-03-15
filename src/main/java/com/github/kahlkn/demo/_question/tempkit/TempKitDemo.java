package com.github.kahlkn.demo._question.tempkit;

import org.junit.Test;

public class TempKitDemo {

    @Test
    public void test3(){
        TempKit.create("123");
        for (int i = 0; i < 11; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; ; i++) {
                        TempKit.use("123").put("abc"+i, i);
                        System.out.println(TempKit.use("123").get("abc"+i));
                        if(i % 100000 == 0)
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }).start();
        }
        for (int i = 0; ; i++) {
            TempKit.use("123").put("abc"+i, i);
            System.out.println(TempKit.use("123").get("abc"+i));
            if(i % 100000 == 0)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    @Test
    public void test5(){
        TempKit.use().put("123", "11111111");
        System.out.println(TempKit.use().get("123"));
        TempKit.use().put("123", null);
        System.out.println(TempKit.use().get("123"));
    }

}
