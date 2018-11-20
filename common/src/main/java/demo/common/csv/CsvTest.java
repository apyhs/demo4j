package demo.common.csv;

import artoria.random.RandomUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvTest {
    private static Map<String, String> ppTitMap = new LinkedHashMap<>();
    private static Map<String, String> titPpMap = new LinkedHashMap<>();

    static {
        ppTitMap.put("name", "用户名");
        ppTitMap.put("pass", "密码");
        ppTitMap.put("gender", "性别");
        for (Map.Entry<String, String> entry : ppTitMap.entrySet()) {
            titPpMap.put(entry.getValue(), entry.getKey());
        }
    }

    @Test
    public void test1() throws IOException {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            userList.add(RandomUtils.nextObject(User.class));
        }
        Csv csv = Csv.create(null, "GB2312", userList, ppTitMap);
        csv.write(new File("e:\\test1.csv"));
    }

    @Test
    public void test2() throws IOException {
        Csv csv = Csv.create(new File("e:\\test1.csv"), "GB2312");
        List<User> users = csv.readToBeans(User.class, titPpMap);
        System.out.println(JSON.toJSONString(users, true));
    }

}
