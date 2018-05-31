package com.github.kahlkn.demo.csv;

import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvDemo {

    @Test
    public void test1(){
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> list = new ArrayList<>();
            Collections.addAll(list, "11", "22", "33", "44", "55", "66");
            data.add(list);
        }
        System.out.println(toCsv(data));
    }

    @Test
    public void test2(){
        List<User> data = new ArrayList<>();
        data.add(new User("zhangsan", "123", "男"));
        data.add(new User("lisi", "123", "男"));
        data.add(new User("wangwu", "123", "男"));
        data.add(new User("zhaoliu", "123", "男"));
        data.add(new User("asdfas", "123", "男"));
    }

    public StringBuffer toCsv(List<List<String>> data) {
        StringBuffer buffer = new StringBuffer();
        for (List<?> list : data) {
            for (Object obj : list) {
                buffer.append(ConvertUtils.convert(obj, String.class)).append(",");
            } buffer.append("\n");
        } return buffer;
    }

}
