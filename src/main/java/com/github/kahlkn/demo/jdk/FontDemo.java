package com.github.kahlkn.demo.jdk;

import org.junit.Test;

import java.awt.*;

public class FontDemo {

    @Test
    public void test1() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = environment.getAvailableFontFamilyNames();//获得系统字体
        for(int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }
    }

}
