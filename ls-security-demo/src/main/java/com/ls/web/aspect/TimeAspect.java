package com.ls.web.aspect;

import com.ls.exception.UserNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;


/*
 * 第一步：声明切片类，加入@Component注解
 * */
@Aspect
@Component
public class TimeAspect {
    /**
     * 第二步，声明切点，使用@Before @After @Around 等注解
     * 配合 execution表达式使用，这个表达式意思据实拦截UserController下面的所有类 ..是所有的参数的意思
     * execution表达式的具体使用在 spring的官方文档 第11章面向切片编程 11.2.3 声明切入点
     * 第三步，织入增强，实现具体业务逻辑方法
     * ProceedingJoinPoint point类似于Interceptor的handle
     * point.proceed()类似于 filter中的filter链，真正执行业务逻辑的方法,这个方法会返回Object
     * 在这个切点是可以拿到参数的 point.getArgs 返回的时候一个数组
     * 可以在这个例子看到，切片
     *
     * 这里有一个重点：如果切片往外抛出去异常，而不是自己处理，如果声明了ControllerAdvice来处理异常，会被外层的ControllerAdvice捕获并处理
     */
    @Around("execution(* com.ls.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint point) throws Throwable {
        System.out.println(" aspect time start");
        Object[] args = point.getArgs();
        for (Object o:
                args) {
            System.out.println("aspect arg is:" + o);
        }
        long time = new Date().getTime();
        Object o = point.proceed();
        System.out.println("aspect 耗时：" + (new Date().getTime()-time ));
        System.out.println(" aspect time end");
        return o;
    }
}
