package demo.common.elasticsearch.example;

import artoria.net.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MainDemo {

    @Test
    public void test1() throws Exception {
        // megacorp  employee
        List<String> ins = new ArrayList<>();
        ins.add("篮球"); ins.add("足球");
        for (int i = 0; i < 100; i++) {
            Employee employee = new Employee()
                    .setNickName("用户" + i)
                    .setRealName("张三" + i)
                    .setAge("2" + i)
                    .setIntro("你好呀")
                    .setInterests(ins);
            System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/" + i + "?pretty")
                    .setData(JSON.toJSONString(employee).getBytes()).put());
        }
    }

    @Test
    public void test2() throws Exception {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(Http.on("http://localhost:9200/megacorp/employee/" + i + "?pretty").get());
//        }
//        System.out.println(Http.on("http://localhost:9200/megacorp/employee/109?pretty").get()); // 即使状态码404，浏览器上还是能有响应的
        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/34/_source?pretty").get());
    }

    @Test
    public void test3() throws Exception {
//        System.out.println(Http.on("http://localhost:9200/megacorp/employee/_search?pretty").get());
        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/_search?q=realName:7&pretty&_source=realName,age").get());
    }

    @Test
    public void test4() throws Exception {
        // head 判断是否 存在，通过 状态码 200 和 404
//        System.out.println(Http.on("http://localhost:9200/megacorp/employee/34?pretty").head());
//        Employee lisi = new Employee().setRealName("李四").setNickName("小四子").setAge("999").setIntro("hello, world! ").setInterests("音乐", "运动", "足球");
//        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/34?pretty").setData(JSON.toJSONString(lisi).getBytes()).put());
//        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/34/_source?pretty").get());
//        Employee who = new Employee().setRealName("钢铁侠").setNickName("小钢").setAge("47").setIntro("飞呀飞呀飞呀飞! ").setInterests("科学", "科技", "机械");
//        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/100?op_type=create").setData(JSON.toJSONString(who).getBytes()).put());
//        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/100/_create").setData(JSON.toJSONString(who).getBytes()).put());
    }

    @Test
    public void test5() throws Exception {
//        Employee who = new Employee().setRealName("绿巨人").setNickName("小绿").setAge("56").setIntro("砸砸砸! ").setInterests("砸", "砸", "砸");
//        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/101/_create?pretty").setData(JSON.toJSONString(who).getBytes()).put());
//        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/101?pretty").delete());
        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/101?pretty").get());
        Employee who1 = new Employee().setRealName("愤怒的绿巨人").setNickName("小绿").setAge("58").setIntro("疯狂的砸砸砸! ").setInterests("疯狂的砸", "疯狂的砸", "疯狂的砸");
        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/101?version=1&pretty").setData(JSON.toJSONString(who1).getBytes()).put());
        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/101?pretty").get());
    }

    @Test
    public void test6() throws Exception {
        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/101?pretty").get());
        System.out.println(HttpUtils.create("http://localhost:9200/megacorp/employee/_mapping?pretty").get());
    }

}
