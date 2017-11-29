package demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JKit {

    /**
     * return data to web
     */
    public static Map<String, Object> make(Integer code){
        Map<String, Object> result = new HashMap<>();
        if (code == null) throw new IllegalArgumentException("code is null ...");
        result.put("code", code); return result;
    }

    /**
     * return data to web
     */
    public static Map<String, Object> make(Integer code, Object data){
        Map<String, Object> result = new HashMap<>();
        if (code == null) throw new IllegalArgumentException("code is null ...");
        result.put("code", code);
        if (data != null) result.put("data", data);
        return result;
    }

    /**
     * exhaustion
     */
    public static void exhaustion(List<List<String>> table, Function<Void, String> func) {
        int len = table.size();
        for(int[] counter = new int[len + 1]; ; ){
            // get now string
            StringBuffer nowString = new StringBuffer();
            for(int i = len - 1; i >= 0; --i)
                nowString.append(table.get(i).get(counter[i]));
            // do something
            func.callback(nowString.toString());
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
