package com.github.kahlkn.demo._function;

import com.github.kahlkn.artoria.net.HttpUtils;
import com.apyhs.artoria.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;

public class SimpleSpider {
    private static final Pattern A_LABEL_PATTERN = Pattern.compile("(?i)<a\\s+(.|\\n)*?</a>");

    @Test
    public void test1() throws Exception {
        String l = "<link  \n rel=' stylesheet   '  \n id   \n=  \n' admin-bar-css  '   href=  \n'   http://uux.me/wp-includes/css/admin-bar.min.css?ver=hello_world '   \n  type  \n  ='text/css' media='all' />";
        Document document = Jsoup.parse(l);
        System.out.println(document.html());
        System.out.println(document.select("link").attr("rel"));
    }

    @Test
    public void test() throws Exception {
        LinkedList<String> list = new LinkedList<>();
        Set<String> result = new HashSet<>();
        String firstUrl = "http://uux.me";
        list.add(firstUrl);
        while (!list.isEmpty()) {
            String url = list.removeFirst();
            System.out.println("正在分析："+url);
            String data = HttpUtils.create(url).get();
            List<String> urls = new ArrayList<>();
            if (data.length() > 900) {
//                while (data.length() > 0) {
//                    if (data.length() > 900) {
//                        urls.addAll(RegexUtils.findAll(A_LABEL_PATTERN, data.substring(0, 900)));
//                        data = data.substring(900);
//                    } else {
//                        urls.addAll(RegexUtils.findAll(A_LABEL_PATTERN, data));
//                        data = "";
//                    }
//                }
//            }
//            else {
//                urls.addAll(RegexUtils.findAll(A_LABEL_PATTERN, data));
            }
            for (String u : urls) {
                Document document = Jsoup.parse(u);
                u = document.select("a").attr("href");
                if (StringUtils.isNotBlank(u) ||
                        u.endsWith(".js") || u.endsWith(".css") ||
                        u.endsWith(".png") || u.endsWith(".jpg") ||
                        u.equals(firstUrl) ||
                        u.equals(firstUrl + "/") || result.contains(u)
                        ) {
                    continue;
                }
                if (u.indexOf("//") == 0) {
                    u = "https:" + u;
                }
                if (!u.contains(firstUrl) && u.indexOf("/") != 0) {
                    System.err.println(u);
                    result.add(u);
                    continue;
                }
                if (u.indexOf("/") == 0) {
                    u = firstUrl + u;
                }
                System.out.println(u);
                result.add(u);
                list.addLast(u);
            }
        }
    }

}
