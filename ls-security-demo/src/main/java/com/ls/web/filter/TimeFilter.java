package com.ls.web.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * @program: ls-security
 * @description: 记录时间filter
 * @author: Liang Shan
 * @create: 2019-11-01 11:13
 **/
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time1 filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("time1 filter start");
        long start = new Date().getTime();
        filterChain.doFilter(servletRequest, servletResponse);
        long end = new Date().getTime();
        System.out.println("time1总共耗时：" + (end - start));
        System.out.println("time1 filter finish");

    }

    @Override
    public void destroy() {
        System.out.println("time1 filter destroy");
    }
}
