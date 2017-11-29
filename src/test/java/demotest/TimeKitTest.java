package demotest;

import demo.TimeKit;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

public class TimeKitTest implements Serializable {

    @Test
    public void test1() {
        System.out.println(TimeKit.ts());
        System.out.println(TimeKit.dayStartTs());
        System.out.println(TimeKit.dayEndTs());
        System.out.println(TimeKit.monthStartTs());
        System.out.println(TimeKit.monthEndTs());
    }

    @Test
    public void test2() {
        System.out.println(TimeKit.tsl());
        System.out.println(TimeKit.dayStartTsl());
        System.out.println(TimeKit.dayEndTsl());
        System.out.println(TimeKit.monthStartTsl());
        System.out.println(TimeKit.monthEndTsl());
    }

    @Test
    public void test3() {
        System.out.println(TimeKit.format());
        Date date = TimeKit.parse("2020-02-22 22:22:22"); // 解析时间字符串
        int ts = TimeKit.ts(date); // 指定的时间戳
        long tsl = TimeKit.tsl(date); // 指定的长时间戳
        System.out.println(ts);
        System.out.println(tsl);
        System.out.println(TimeKit.format(ts)); // 格式化时间戳
        System.out.println(TimeKit.format(tsl)); // 格式化长时间戳
        System.out.println(TimeKit.format(TimeKit.parseTs(ts))); // 解析时间戳，并格式化时间
        System.out.println(TimeKit.format(TimeKit.parseTsl(tsl))); // 解析长时间戳，并格式化时间
    }

    @Test
    public void test4() {
        System.out.println(TimeKit.format(TimeKit.setDate(null, 2012, 12, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, null, 12, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, null, null, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, null, null, null, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, null, null, null, null, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, null, null, null, null, null, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, null, null, null, null, null, null, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, null, null, null, null, null, null, null)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 2012, 12, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 0, 0, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 1, 0, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 0, 1, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 1, 1, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 1, 0, 1, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 0, 1, 1, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 1, 1, 1, 0, 0, 0, 0)));
    }

    @Test
    public void test5() {
        System.out.println(TimeKit.format(TimeKit.date(2012, 12, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.date(2012, 12, 12, 12, 12, 0, 122)));
        System.out.println(TimeKit.format(TimeKit.date(2012, 12, 12, 12, 0, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.date(2012, 12, 12, 0, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.date(2012, 12, 0, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.date(2012, 0, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.date(0, 12, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.date(0, 0, 12, 12, 12, 12, 122)));
        System.out.println(TimeKit.format(TimeKit.date(0, 0, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.date(1, 0, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.date(0, 1, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.date(1, 1, 0, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.date(1, 0, 1, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.date(0, 1, 1, 0, 0, 0, 0)));
        System.out.println(TimeKit.format(TimeKit.date(1, 1, 1, 0, 0, 0, 0)));
    }

    @Test
    public void test6() {
        System.out.println(TimeKit.format(TimeKit.setDate(null, 1, 0, 1)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 0, 1, 1)));
        System.out.println(TimeKit.format(TimeKit.setDate(null, 1, 1, 1)));
        System.out.println(TimeKit.format(TimeKit.date(1, 0, 1)));
        System.out.println(TimeKit.format(TimeKit.date(0, 1, 1)));
        System.out.println(TimeKit.format(TimeKit.date(1, 1, 1)));
    }

    @Test
    public void test7() {
//        Date date = new Date();
        Date date = TimeKit.date(0, 0, 0, 0, 0, 0, 0);
        System.out.println(TimeKit.getYear(date));
        System.out.println(TimeKit.getMonth(date));
        System.out.println(TimeKit.getDay(date));
        System.out.println(TimeKit.getHour(date));
        System.out.println(TimeKit.getMinute(date));
        System.out.println(TimeKit.getSecond(date));
        System.out.println(TimeKit.getMillisecond(date));
    }

    @Test
    public void test8() {
        Date date = TimeKit.date(1001, 1, 1, 0, 0, 0, 0);
        System.out.println(TimeKit.format(date));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, 1, 1, 1, 1, 1, 1, 1)));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, 1000, null, null, null, null, null, null)));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, null, 24, null, null, null, null, null)));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, null, null, 60, null, null, null, null)));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, null, null, null, 48, null, null, null)));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, null, null, null, null, 120, null, null)));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, null, null, null, null, null, 120, null)));
        System.out.println(TimeKit.format(TimeKit.addToDate(date, null, null, null, null, null, null, 6000)));
    }

}
