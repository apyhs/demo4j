package demo.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("Proxy = " + proxy.getClass() + ", method = " + method.getName() + ", args = " + Arrays.toString(args) + ", methodProxy = " + methodProxy.getSuperName());
                System.out.println("调用前");
//                Object result = methodProxy.invokeSuper(object, args);
                Object result = method.invoke(subject, args);
                System.out.println("调用后");
                return result;
            }
        });
        Subject subjectProxy = (Subject) enhancer.create();
        System.out.println(subjectProxy.getClass());

        System.out.println(subjectProxy.sayHello("张三"));
        System.out.println(subjectProxy.sayGoodBye());
    }

}
