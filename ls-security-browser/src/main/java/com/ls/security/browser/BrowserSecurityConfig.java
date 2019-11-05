package com.ls.security.browser;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: Liang Shan
 * @date: 2019-11-05 14:13
 * @description: 网页安全配置
 * 表单登录：
 * 1. 继承WebSecurityConfigurerAdapter
 * 2. 覆写configure(HttpSecurity http)
 */
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定义认证方式为表单登录
        http.formLogin()
                // 定义对下列都是授权配置
                .and().authorizeRequests()
                // 对所有请求做授权
                .anyRequest()
                // 都需要身份认证
                .authenticated();

    }
}
