package com.ls.web.config;

import com.ls.web.filter.TimeFilter;
import com.ls.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ls-security
 * @description: web配置类
 * 注册 interceptor 还必须在这个web配置类 实现 WebMvcConfigurer 实现其addInterceptors方法，将 我们写的拦截器添加进去即可
 * @author: Liang Shan
 * @create: 2019-11-01 14:22
 **/
@Configuration // 这个注解可以把所有
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TimeInterceptor timeInterceptor;
    /*注册定义好的拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    /**
    * @Description:  这个其实就是返回一个Filter类型的注册bean ，相当于在web.xml上配置bean了
    * @Param:
    * @return:
    * @Author: Liang Shan
    * @Date: 2019/11/1 0001
    */
    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        // 把这个filter注入到filter配置管理Bean中
        filterRegistrationBean.setFilter(timeFilter);
        // 还可以自定义过滤的url
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
}
