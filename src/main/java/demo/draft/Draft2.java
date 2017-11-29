package demo.draft;

import kit4j.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Draft2 {

    @Test
    public void test1(){
        try {
            byte[] input = "abcdef123456".getBytes();
            Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod= clazz.getMethod("encode", byte[].class);
            mainMethod.setAccessible(true);
            Object retObj=mainMethod.invoke(null, new Object[]{input});
            System.out.println((String)retObj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test2(){
        try {
            String input = "YWJjZGVmMTIzNDU2";
            Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod= clazz.getMethod("decode", String.class);
            mainMethod.setAccessible(true);
            Object retObj=mainMethod.invoke(null, input);
            System.out.println(Arrays.toString((byte[])retObj));
            System.out.println(new String((byte[])retObj));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test3(){
        byte[] input = "abcdef123456".getBytes();
        String base64 = CodeKit.encodeBase64(input);
        System.out.println(base64);
        System.out.println(new String(CodeKit.decodeBase64(base64)));
    }

    @Test
    public void test4(){
//        String url = "https://p.10086.cn/";
//        String url = "http://m.aliyun.com/";
        String url = "https://www.baidu.com/";
        Map<String, String> query = new HashMap<>();
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36");
        String s = HttpKit.get(url, query, header);
        System.out.println(s);
    }

    @Test
    public void test5(){
        for (int i = 0; i < 1000; i++) {
            String url = abc("安萌特的随想录");
            System.out.println(StrKit.notBlank(HttpKit.get(url)));
            ThreadKit.sleep(60000);
        }
    }
    
    public String abc(String kw) {
        String html = HttpKit.get("https://www.baidu.com/s?wd=" + kw);
        Document document = Jsoup.parse(html);
        Element element = document.getElementsByAttributeValue("id", "content_left").first();
        element = element.getElementsContainingOwnText(kw).first().parent().parent();
        String result = element.html();
        result = result.substring(result.indexOf("href=\"") + 6);
        result = result.substring(0, result.indexOf("\" target=\"_blank\""));
        return result;
    }

    @Test
    public void t1(){
        String data = ",,1,,2";
        String[] split = data.split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }


}
