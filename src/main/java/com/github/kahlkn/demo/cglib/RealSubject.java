package com.github.kahlkn.demo.cglib;

public class RealSubject implements Subject {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    @Override
    public String sayGoodBye() {
        return "Good bye";
    }

}
