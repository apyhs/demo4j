package demo.common.cglib;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private Integer sex;
    private Integer age;
    private Long money;

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSex() {
        return sex;
    }

    public Person setSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Person setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Long getMoney() {
        return money;
    }

    public Person setMoney(Long money) {
        this.money = money;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", money=" + money +
                '}';
    }
}
