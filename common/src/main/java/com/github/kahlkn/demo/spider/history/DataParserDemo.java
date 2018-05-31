package com.github.kahlkn.demo.spider.history;

import org.junit.Before;
import org.junit.Test;

public class DataParserDemo {

    private String data;
    private String template;

    @Before
    public void init() {
        // String regex = "\\$\\{.[^$,^{,^}]*\\}";

        data = "  dfd fdfs ddsds dfdsf    dsfqw   assddas gdfg \n" +
                "sadsa sasdf sdf sdfsdhert   fdzx awet fbre\n" +
                "adsf asd facvf fdf gdf awerd sdfvcb dser vcvbc\n" +
                "sdfgsdfdsgs sdfds sdfgsd sdgfds frhfher srgfw evbc\n" +
                "fas weewq sdwkuy uydfg gfdrg dgxx dfhdf hdfd thvbx dfdfg\n   ";

        template = "      dfd f${str1}fs ddsds dfdsf    ds${str2}   assddas gdfg \n" +
                "sadsa sasdf sdf sdf${str3}t   fdzx awet fbre\n" +
                "adsf asd facvf fdf gdf awerd sdfvcb dser vcvbc\n" +
                "sdfgsd${str4}rgfw${str5}drg dgxx dfhdf hdfd thvbx dfdfg  \n";

    }

    @Test
    public void test1() {
        // 空格 预处理
        template = template.trim();
        data = data.trim();

        int index = template.indexOf("${");
        for (; index != -1; ) {

            String start = template.substring(0, index);
            // 处理模板
            template = template.substring(index + 2);
            // 处理数据
            index = data.indexOf(start);
            data = data.substring(index + start.length());


            index = template.indexOf("}");
            if (index == -1) {
                throw new RuntimeException("语法错误，找不到对应的“}”符号。" + template);
            }
            // 获取 花括号 里面 的 内容
            String inside = template.substring(0, index);
            if (inside.contains("${")) {
                throw new RuntimeException("语法错误，缺少“}”符号" + template);
            }
            System.out.print(inside); // 花括号 内容 展示


            // 获取 } 之后，${ 之前的 内容
            template = template.substring(index + 1);
            int nextIndex = template.indexOf("${");
            String tmp = nextIndex != -1 ? template.substring(0, nextIndex) : template;
            System.out.print(" -- ");

            index = data.indexOf(tmp);
            String mapData = data.substring(0, index);
            System.out.print(mapData);
            data = data.substring(index);
            index = nextIndex;
            System.out.println();
        }
    }

}
