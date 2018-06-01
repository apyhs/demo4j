package com.github.kahlkn.demo4j.function;

import java.util.List;

public abstract class Exhaustion implements Runnable {
    private List<List<String>> table;
    private int len;
    private int[] counter;

    public Exhaustion(List<List<String>> table) {
        this.table = table;
        this.len = table.size();
        counter = new int[len + 1];
    }

    public String getCounter() {
        StringBuilder s = new StringBuilder();
        for (int i = len; i >= 0; i--) {
            s.append(counter[i]);
        }
        return s.toString();
    }

    @Override
    public void run() {
        for(; ; ) {
            // get now string
            StringBuilder nowString = new StringBuilder();
            for(int i = len - 1; i >= 0; --i) {
                nowString.append(table.get(i).get(counter[i]));
            }
            // do something
            handle(nowString.toString());
            // clear nowString
            nowString.setLength(0);
            // counter increment
            for (int i = 0; i < len; i++) {
                int tmp = counter[i] + 1;
                if(tmp == table.get(i).size()) {
                    if(i == len - 1) {
                        counter[len - 1] = 0;
                        counter[len] = 1; break;
                    } else {
                        counter[i] = 0;
                        // continue;
                    }
                } else {
                    counter[i] = tmp; break;
                }
            }
            // is ok?
            if(counter[len] == 1) break;
        }
    }

    public abstract void handle(String nowString);

}
