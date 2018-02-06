package com.apyhs.demo.artoria.beans;

import com.apyhs.artoria.beans.BeanUtils;
import com.apyhs.artoria.beans.CglibBeanMap;
import com.apyhs.artoria.support.entity.Menu;
import com.apyhs.artoria.util.RandomUtils;
import net.sf.cglib.beans.BeanMap;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class BeanMapPerformance {
    private static final Integer count = 50;
    private static final Integer loopCount = 100000;
    private static final Menu MENU = new Menu();

    static {
        MENU.setId(1);
        MENU.setCode(RandomUtils.nextUUID());
        MENU.setName("MENU");
        MENU.setModule("Hello");
        MENU.setParentCode(RandomUtils.nextUUID());
    }

    @Test
    public void cglib() {
        System.out.print("Cglib: ");
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < loopCount; j++) {
                BeanMap beanMap = BeanMap.create(MENU);
                beanMap.get("code");
                beanMap.get("id");
            }
            long end = System.currentTimeMillis();
            System.out.print((end - start) + " ");
            System.gc();
        }
        System.out.println();
    }

    @Test
    public void apache() throws InvocationTargetException, IllegalAccessException {
        System.out.print("Apache: ");
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < loopCount; j++) {
                org.apache.commons.beanutils.BeanMap beanMap = new org.apache.commons.beanutils.BeanMap(MENU);
                beanMap.get("code");
                beanMap.get("id");
            }
            long end = System.currentTimeMillis();
            System.out.print((end - start) + " ");
            System.gc();
        }
        System.out.println();
    }

    @Test
    public void artoria() {
        BeanUtils.setBeanMapClass(CglibBeanMap.class);
        System.out.print("Artoria: ");
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < loopCount; j++) {
                com.apyhs.artoria.beans.BeanMap beanMap = BeanUtils.createBeanMap(MENU);
                beanMap.get("code");
                beanMap.get("id");
            }
            long end = System.currentTimeMillis();
            System.out.print((end - start) + " ");
            System.gc();
        }
        System.out.println();
    }

}
