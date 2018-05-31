package com.github.kahlkn.demo.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

// @Component
@Aspect
public abstract class AspectComponent {

    @Pointcut("execution(* @demo.spring.AspectFlag *.*(..))")
    protected void pointcutClass() {}

    @Pointcut("execution(@demo.spring.AspectFlag * *(..))")
    protected void pointcutMethod() {}

    @Before("pointcutClass() || pointcutMethod()")
    public void doBefore() {
    }

    @Around("pointcutClass() || pointcutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    @After("pointcutClass() || pointcutMethod()")
    public void doAfter() {
    }

    @AfterReturning(pointcut = "pointcutClass() || pointcutMethod()", returning = "result")
    public void doAfterReturning(String result) {
    }

    @AfterThrowing(pointcut = "pointcutClass() || pointcutMethod()", throwing = "e")
    public void doAfterThrowing(Throwable e) {
    }

}
