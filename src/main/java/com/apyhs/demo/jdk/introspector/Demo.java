package com.apyhs.demo.jdk.introspector;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Demo {

    @Test
    public void test1() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Student.class);

        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null) {
            System.out.println("PropertyDescriptors is null");
            return;
        }
        for (PropertyDescriptor descriptor : descriptors) {
            Method readMethod = descriptor.getReadMethod();
            Method writeMethod = descriptor.getWriteMethod();
            System.out.println(
                    (readMethod != null ? readMethod.getName() : "no read method") + " | " +
                    (writeMethod != null ? writeMethod.getName() : "no write method")
            );
            // System.out.println(descriptor.getPropertyType());
        }
    }

    @Test
    public void test2() {
//        Class<?> clazz = Student.class;
        Class<?> clazz = Person.class;
//        Method[] methods = clazz.getMethods();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    @Test
    public void test3() {
        Class<?> clazz = Student.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        System.out.println("-----------------");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
        }
    }

}
