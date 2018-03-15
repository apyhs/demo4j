package com.github.kahlkn.demo.jdk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExhaustionDemo {

    @Test
    public void testExhaustivity() {
        // 构造 table
        List<List<String>> table = new ArrayList<>();
        exec(table);
    }

    public void doSomething(String nowString){
        // 得到你想要 穷举的字符串，做你想做的
        System.out.println(nowString);
    }

    public void exec(List<List<String>> table) {
        int len = table.size();
        for(int[] counter = new int[len + 1]; ; ){
            // get now string
            StringBuilder nowString = new StringBuilder();
            for(int i = len - 1; i >= 0; --i)
                nowString.append(table.get(i).get(counter[i]));
            // do something
            doSomething(nowString.toString());
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
                        counter[i] = 0; continue;
                    }
                } else { counter[i] = tmp; break; }
            }
            // is ok?
            if(counter[len] == 1) break;
        }
    }

}
