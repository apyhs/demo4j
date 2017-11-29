package demo.jdk.util;

import java.util.List;

public abstract class Looper {

    public abstract void handle(String nowString);

    public void exec(List<List<String>> table) {
        int len = table.size();
        for(int[] counter = new int[len + 1]; ; ) {
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
                        counter[i] = 0; continue;
                    }
                } else {
                    counter[i] = tmp; break;
                }
            }
            // is ok?
            if(counter[len] == 1) break;
        }
    }

}
