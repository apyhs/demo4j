package demo.freemarker;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class Demo1 {

    @Test
    public void test1() throws Exception {
        Map root = new HashMap<>();
        root.put("str", "hello world!");
        List data = new ArrayList();
        data.add("11");
        data.add("12");
        data.add("13");
        root.put("data", data);

        Configuration cfg = new Configuration();
        URL res = Demo1.class.getClassLoader().getResource("");
        File file = res != null ? new File(res.toURI().getPath(), "templates") : null;
        cfg.setDirectoryForTemplateLoading(file);
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        Template temp = cfg.getTemplate("demo.ftl");
        StringWriter writer = new StringWriter();
        BufferedWriter bw = new BufferedWriter(writer);
        temp.process(root, bw);
        bw.flush();
        System.out.println(writer.toString());
    }

}
