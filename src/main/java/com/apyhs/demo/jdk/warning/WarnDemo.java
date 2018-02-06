package com.apyhs.demo.jdk.warning;

import org.junit.Test;

import java.util.*;

public class WarnDemo {

    @Test
    public void test0(){
        // for(;;);
        // for(List l = new ArrayList();;l.add(new SoftReference<>(new Object())));
        // for(new Thread(new Runnable(){public void run(){for(;;);}}).start();;);
        for(;;new Thread(new Runnable(){public void run(){for(;;);}}).start());
    }

    @Test
    public void test4(){
        Map<Object, String> map = new HashMap<>();
        map.put("123", null);
        map.put(null, "aaa");
        System.out.println(map.get(null));
        System.out.println(map.size());
        System.out.println(map.get("123"));
        System.out.println(map.remove("123"));
        System.out.println(map.size());
    }

    @Test
    public void t1(){
        String data = ",,1,,2,";
        String[] split = data.split(",");
        for (String s : split) {
            System.out.println(s);
        }
        System.out.println(split.length);
    }

    @Test
    public void te1() {
        Object obj = null;
        int i = (int) obj;
        System.out.println(i);
    }

    @Test
    public void te2() {
        Object obj = null;
        Integer i = (Integer) obj;
        System.out.println(i);
    }

    @Test
    public void te3() {
        List<String> list = new ArrayList<>();
        list.set(10, "aaaaa");
    }

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "aa", "bb", "cc", "dd", "ee", "ff", "gg");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + ", ");
            if (i == 2) {
                list.remove(i);
                System.out.print(list.get(i) + ", ");
            }
        }
        System.out.println();
        System.out.println(list);
    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "aa", "bb", "cc", "dd", "ee", "ff", "gg");
        for (String s : list) {
            if ("cc".equals(s)) list.remove(s);
        }
        System.out.println(list);
    }

    @Test
    public void test3() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "aa", "bb", "cc", "dd", "ee", "ff", "gg");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.print(next + ", ");
            if ("cc".equals(next)) iterator.remove();
        }
        System.out.println();
        System.out.println(list);
    }

    @Test
    public void test10() throws Exception {
        Runtime.getRuntime().exec(
                System.getenv("windir") + "\\system32\\shutdown.exe -s -f");
    }

}
