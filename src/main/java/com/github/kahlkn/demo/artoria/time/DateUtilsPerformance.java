package com.github.kahlkn.demo.artoria.time;

import com.apyhs.artoria.support.test.Item;
import com.apyhs.artoria.support.test.Performance;
import com.apyhs.artoria.support.test.Report;
import com.apyhs.artoria.support.test.Tested;
import com.github.kahlkn.artoria.time.DateTime;
import com.github.kahlkn.artoria.time.DateUtils;
import org.junit.Test;

import java.util.Date;

public class DateUtilsPerformance {
    private static final Integer groupCount = 10;
    private static final Integer count = 30;
    private static final Integer innerCount = 100000;

    @Test
    public void test1() throws Exception {
        // 32 14 200 138
        final Date date = new Date();
        Item single = Performance.single(groupCount, count, innerCount, new Tested() {
            @Override
            public void call() throws Exception {
                DateUtils.format(date);
            }
        });
        Item multiple = Performance.multiple(groupCount, count, innerCount, new Tested() {
            @Override
            public void call() throws Exception {
                DateUtils.format(date);
            }
        });
        Report report = new Report();
        report.addItem(single);
        report.addItem(multiple);
        System.out.println(report);
    }

    @Test
    public void test2() throws Exception {
        // 4 6 4 6
        // 28 13 157 116
        final Date date = new Date();
        Item single = Performance.single(groupCount, count, innerCount, new Tested() {
            @Override
            public void call() throws Exception {
                DateTime.create();
            }
        });
        Item multiple = Performance.multiple(groupCount, count, innerCount, new Tested() {
            @Override
            public void call() throws Exception {
                DateTime.create();
            }
        });
        Report report = new Report();
        report.addItem(single);
        report.addItem(multiple);
        System.out.println(report);
    }


}
