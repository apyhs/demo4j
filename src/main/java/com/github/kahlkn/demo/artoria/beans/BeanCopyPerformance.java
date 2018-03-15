package com.github.kahlkn.demo.artoria.beans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.apyhs.artoria.support.test.Item;
import com.apyhs.artoria.support.test.Performance;
import com.apyhs.artoria.support.test.Report;
import com.apyhs.artoria.support.test.Tested;
import com.github.kahlkn.artoria.beans.CglibBeanCopier;
import com.github.kahlkn.artoria.converter.ConvertUtils;
import com.github.kahlkn.artoria.util.RandomUtils;
import com.github.kahlkn.demo.artoria.entity.Menu;
import com.github.kahlkn.demo.artoria.entity.MenuVO;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

public class BeanCopyPerformance {
    private static final Integer groupCount = 10;
    private static final Integer count = 30;
    private static final Integer innerCount = 50000;
    private static final Menu MENU = new Menu();
    private static final Converter CONVERTER = new Converter() {
        @Override
        public Object convert(Object o, Class aClass, Object o1) {
            return ConvertUtils.convert(o, aClass);
        }
    };

    static {
        MENU.setId(1);
        MENU.setCode(RandomUtils.nextUUID());
        MENU.setName("MENU");
        MENU.setModule("Hello");
        MENU.setParentCode(RandomUtils.nextUUID());
    }

    @Test
    public void test() throws Exception {
        Report report = new Report();
        cglib(report);
        artoria(report);
        fastJson(report);
        apache(report);
        report.setTitle("Cglib, Apache, FastJson, Artoria bean copy performance test report");
        System.out.println(report.toString());
    }

    public void cglib(Report report) throws Exception {
        BeanCopier.create(Menu.class, MenuVO.class, true);
        final MenuVO vo = new MenuVO();
        Tested tested = new Tested() {
            @Override
            public void call() throws Exception {
                BeanCopier copier = BeanCopier.create(Menu.class, MenuVO.class, true);
                copier.copy(MENU, vo, CONVERTER);
                vo.getCode();
            }
        };
        Item singleItem = Performance.single(groupCount, count, innerCount, tested).setTitle("Cglib BeanCopier single Thread");
        report.addItem(singleItem);
        Item multipleItem = Performance.multiple(groupCount, count, innerCount, tested).setTitle("Cglib multiple Thread");
        report.addItem(multipleItem);
    }

    public void apache(Report report) throws Exception {
        final MenuVO vo = new MenuVO();
        BeanUtils.copyProperties(vo, MENU);
        Tested tested = new Tested() {
            @Override
            public void call() throws Exception {
                BeanUtils.copyProperties(vo, MENU);
                vo.getCode();
            }
        };
        Item singleItem = Performance.single(groupCount, count, innerCount, tested).setTitle("Apache BeanUtils single Thread");
        report.addItem(singleItem);
        Item multipleItem = Performance.multiple(groupCount, count, innerCount, tested).setTitle("Apache BeanUtils multiple Thread");
        report.addItem(multipleItem);
    }

    public void artoria(Report report) throws Exception {
        com.github.kahlkn.artoria.beans.BeanUtils.setBeanCopier(new CglibBeanCopier());
        final MenuVO vo = new MenuVO();
        com.apyhs.artoria.beans.BeanUtils.copy(MENU, vo);
        Tested tested = new Tested() {
            @Override
            public void call() throws Exception {
                com.apyhs.artoria.beans.BeanUtils.copy(MENU, vo);
                vo.getCode();
            }
        };
        Item singleItem = Performance.single(groupCount, count, innerCount, tested).setTitle("Artoria BeanUtils single Thread");
        report.addItem(singleItem);
        Item multipleItem = Performance.multiple(groupCount, count, innerCount, tested).setTitle("Artoria BeanUtils multiple Thread");
        report.addItem(multipleItem);
    }

    public void fastJson(Report report) throws Exception {
        String jsonString = JSON.toJSONString(MENU, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.QuoteFieldNames);
        MenuVO vo = JSON.parseObject(jsonString, MenuVO.class);
        Tested tested = new Tested() {
            @Override
            public void call() throws Exception {
                String jsonString = JSON.toJSONString(MENU, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.QuoteFieldNames);
                MenuVO vo = JSON.parseObject(jsonString, MenuVO.class);
                vo.getCode();
            }
        };
        Item singleItem = Performance.single(groupCount, count, innerCount, tested).setTitle("FastJson toJSONString and parseObject single Thread");
        report.addItem(singleItem);
        Item multipleItem = Performance.multiple(groupCount, count, innerCount, tested).setTitle("FastJson toJSONString and parseObject multiple Thread");
        report.addItem(multipleItem);
    }

}
