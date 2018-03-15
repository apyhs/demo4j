package com.github.kahlkn.demo._question;

/**
 * 静态代码块 测试
 */
public class StaticStuff {

    static int x = 10;

    static {
        System.out.println("5 x = " + x);
        x *= 5;
    }

    public static void main(String[] args) {
        System.out.println("x = " + x);
    }

    static {
        System.out.println("3 x = " + x);
        x /= 3;
    }

}
