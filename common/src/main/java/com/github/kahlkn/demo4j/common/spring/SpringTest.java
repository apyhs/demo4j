package com.github.kahlkn.demo4j.common.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    @Test
    public void test() {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
            new String [] {"applicationContext.xml"}
        );
        TestBean testBean = (TestBean) appContext.getBean("testBean");
        System.out.println(testBean);
    }

}
