package com.ls.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * @program: ls-security
 * @description:
 * @author: Liang Shan
 * @create: 2019-11-01 11:50
 **/
@Component(value = "2time")
public class Time2Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time2 filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("time2 filter start");
        long start = new Date().getTime();
        filterChain.doFilter(servletRequest, servletResponse);
        long end = new Date().getTime();
        System.out.println("time2总共耗时：" + (end - start));
        System.out.println("time2 filter finish");

    }

    @Override
    public void destroy() {
        System.out.println("time2 filter destroy");
    }
}
