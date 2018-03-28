package com.github.kahlkn.demo.jdk;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionsDemo {

    private static class Student {
        private String name;
        private Integer age;

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @Test
    public void test1() {
        List<Student> list = new ArrayList<>();
        Collections.addAll(list
                , new Student("zhangsan", 19)
                , new Student("lisi", 23)
                , new Student("wangwu", 17)
                , new Student("zhaoliu", 26)
        );
        System.out.println(JSON.toJSONString(list));

        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge() - o2.getAge();
            }
        });

        System.out.println(JSON.toJSONString(list));
    }

}
