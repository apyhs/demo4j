package com.apyhs.demo.jdk.invocation;

import com.apyhs.artoria.util.ClassUtils;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Demo {

    @Test
    public void test1() {
        System.out.println("正常调用");
        Subject subject = new RealSubject();
        System.out.println(subject.sayHello("张三"));
        System.out.println(subject.sayGoodBye());
    }

    @Test
    public void test2() {
        System.out.println("代理调用");
        Subject subject = new RealSubject();

        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        // 被代理类的所有接口数组
        Class<?>[] interfaces = RealSubject.class.getInterfaces();
        Subject subjectProxy = (Subject) Proxy.newProxyInstance(loader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(proxy.getClass());
                System.out.println("在调用之前哈，被调用的方法为：" + method.getName());
                // 调用原先的
                Object result = method.invoke(subject, args);
                System.out.println("在调用之后，调用的返回值为：" + result);
                return result;
            }
        });
        System.out.println(subjectProxy.getClass());
        System.out.println(subjectProxy.sayHello("张三"));
        System.out.println(subjectProxy.sayGoodBye());
    }


}
