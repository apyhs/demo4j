package com.github.kahlkn.demo.elasticsearch.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Employee {
    private String nickName;
    private String realName;
    private String age;
    private String intro;
    private List<String> interests;

    public String getNickName() {
        return nickName;
    }

    public Employee setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public Employee setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getAge() {
        return age;
    }

    public Employee setAge(String age) {
        this.age = age;
        return this;
    }

    public String getIntro() {
        return intro;
    }

    public Employee setIntro(String intro) {
        this.intro = intro;
        return this;
    }

    public List<String> getInterests() {
        return interests;
    }

    public Employee setInterests(List<String> interests) {
        this.interests = interests;
        return this;
    }

    public Employee setInterests(String... interests) {
        this.interests = new ArrayList<>();
        this.interests.addAll(Arrays.asList(interests));
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", age='" + age + '\'' +
                ", intro='" + intro + '\'' +
                ", interests=" + interests +
                '}';
    }

}
