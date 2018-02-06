package com.apyhs.demo.jdk;

import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ReferenceDemo {

    @Test
    public void test1() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("haha", "你好，世界");
        WeakReference<Map<String, Object>> wf = new WeakReference<>(map);
        map = null;
        System.out.println(wf);
        System.out.println(wf.get());
        System.gc();
        System.out.println(wf);
        System.out.println(wf.get());

    }

}
