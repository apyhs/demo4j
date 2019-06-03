package demo.cglib;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.core.Converter;
import org.junit.Test;

public class BeanCopierDemo {

    @Test
    public void test_beanToMap() {
        Person p = new Person().setName("张三").setSex(1).setAge(19).setMoney(1000L);
        BeanMap beanMap = BeanMap.create(p);
        System.out.println(beanMap);
        Person p1 = new Person().setName("李四").setSex(0).setAge(22).setMoney(1000L);
        beanMap.setBean(p1);
        System.out.println(beanMap);

        Object bean = beanMap.getBean();
        System.out.println(bean);
    }

    @Test
    public void test_BeanCopieConverterFalse() {
        Person p = new Person().setName("张三").setSex(1).setAge(19).setMoney(1000L);
        BeanCopier beanCopier = BeanCopier.create(Person.class, Student.class, false);
        Student student = new Student();
        beanCopier.copy(p, student, null);
        System.out.println(student);
    }

    @Test
    public void test_BeanCopierConverterTrue() {
        Person p = new Person().setName("张三").setSex(1).setAge(19).setMoney(1000L);
        BeanCopier beanCopier = BeanCopier.create(Person.class, Superman.class, true);
        Superman superman = new Superman();
        beanCopier.copy(p, superman, new Converter() {
            @Override
            public Object convert(Object value, Class target, Object context) {
                // source object property value
                System.out.print(value);
                // target object property class
                System.out.print(" " + target);
                // setter method name
                System.out.println(" " + context);
                return null;
            }
        });
        System.out.println(superman);
    }

}
