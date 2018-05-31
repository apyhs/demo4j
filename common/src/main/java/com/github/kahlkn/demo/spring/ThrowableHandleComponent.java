package com.github.kahlkn.demo.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ThrowableHandleComponent {

    @Pointcut("execution(* @demo.spring.ThrowableHandle *.*(..))")
    protected void pointcutClass() {}

    @Pointcut("execution(@demo.spring.ThrowableHandle * *(..))")
    protected void pointcutMethod() {}

    @Before("pointcutClass() || pointcutMethod()")
    public void doBefore() {
        System.out.println("前置通知");
    }

    @AfterReturning(pointcut = "pointcutClass() || pointcutMethod()", returning = "result")
    public void doAfterReturning(String result) {
        System.out.println("后置通知");
        System.out.println("---" + result + "---");
    }

    @AfterThrowing(pointcut = "pointcutClass() || pointcutMethod()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        System.out.println("例外通知");
        System.out.println(e.getMessage());
    }

    @After("pointcutClass() || pointcutMethod()")
    public void doAfter() {
        System.out.println("最终通知");
    }

    @Around("pointcutClass() || pointcutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入方法---环绕通知");
        Object o = pjp.proceed();
        System.out.println("退出方法---环绕通知");
        return o;
    }

}
