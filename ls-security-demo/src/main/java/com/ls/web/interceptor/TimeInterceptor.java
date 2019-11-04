package com.ls.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @program: ls-security
 * @description: JAVA拦截器
 * 实现HandlerInterceptor接口 ,实现三个方法
 * 但是只声明拦截器是没有用的，还必须有一些配置的
 * @author: Liang Shan
 * @create: 2019-11-01 16:08
 **/
@Component
public class TimeInterceptor implements HandlerInterceptor {
    /**
     * @Description: 在执行控制器方法，也就是Controller真正执行代码的那个方法被调用之前，那个方法会被调用。
     * @Param:
     * @return:
     * @Author: Liang Shan
     * @Date: 2019/11/1 0001
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        System.out.println("preHandle");
        // 因为是两个不同时期的方法体，所以两个方法传值需要这样使用request的attribute来传值
        request.setAttribute("startTime", new Date().getTime());
        // 而方法传入的形参 handler 就是真正进行业务处理的handler
        System.out.println("从拦截器获取的控制器类名:" + ((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println("从拦截器获取的控制方法名:" + ((HandlerMethod) handler).getMethod().getName());

        // preHandler 的返回值 决定了后面是否还执行post Handler和 afterCompletion
        return true;
    }

    /**
    * @Description: 在控制器方法处理之后，post方法会被调用，如果控制器方法中抛出异常，那么这个方法就不会被调用了，
    * @Param: [request, response, handler, modelAndView]
    * @return: void
    * @Author: Liang Shan
    * @Date: 2019/11/1 0001
    */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 如果不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return ;
        }
        System.out.println("postHandle");
        long startTime = (long) request.getAttribute("startTime");
        System.out.println("time interceptor 时间耗时:" + (startTime - new Date().getTime()));

    }

    /**
    * @Description: 在都完成后执行的方法，如果执行方法抛出异常，post不执行，该方法执行
    * @Param: [request, response, handler, ex]
    * @return: void
    * @Author: Liang Shan
    * @Date: 2019/11/1 0001
    */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");

    }
}
