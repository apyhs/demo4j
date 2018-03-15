package com.github.kahlkn.demo.jsoup;

import com.jfinal.kit.HttpKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class JsoupDemo {

    @Test
    public void test() {
        String kw = "安萌特的随想录";
        String html = HttpKit.get("https://www.baidu.com/s?wd=" + kw);
        Document document = Jsoup.parse(html);
        Element element = document.getElementsByAttributeValue("id", "content_left").first();
        element = element.getElementsContainingOwnText(kw).first().parent().parent();
        String result = element.html();
        result = result.substring(result.indexOf("href=\"") + 6);
        result = result.substring(0, result.indexOf("\" target=\"_blank\""));
        System.out.println(result);
    }

}
