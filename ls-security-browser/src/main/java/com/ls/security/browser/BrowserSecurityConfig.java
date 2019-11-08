package com.ls.security.browser;

import com.ls.security.core.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author: Liang Shan
 * @date: 2019-11-05 14:13
 * @description: spring security配置
 * 表单登录：
 * 1. 继承WebSecurityConfigurerAdapter
 * 2. 覆写configure(HttpSecurity http)
 */
@Configuration
@EnableWebSecurity // spring boot 项目没有必要打上这个注解
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private SecurityProperties securityProperties;
    /*
    * @author: Liang Shan
    * @date: 2019-11-06
    * @time: 11:25
    * @param:
    * @description: 注册一个PasswordEncoder 的实例，在后面我们只需@Autowired PasswordEncoder  就可以拿到我们实现好的方法
    * passwordEncoder 的encode（）方法是在用户注册存入数据库时用户自己调用的，是存入数据库做加密使用的，
    * matches（）方法就是将用户传上来的密码与数据库中的密码做匹配，返回一个布尔类型，这个是security调用的，用来在登录时验证密码是否正确的
    * BCryptPasswordEncoder 实现了PasswordEncoder接口
    * BCryptPasswordEncoder相关知识：
    * 用户表的密码通常使用MD5等不可逆算法加密后存储，为防止彩虹表破解更会先使用一个特定的字符串（如域名）加密，然后再使用一个随机的salt（盐值）加密。
    * 特定字符串是程序代码中固定的，salt是每个密码单独随机，一般给用户表加一个字段单独存储，比较麻烦。
    * BCrypt算法将salt随机并混入最终加密后的密码，验证时也无需单独提供之前的salt，从而无需单独处理salt问题。
    * 所以相同的明文密码，存储在数据库中，都是不同的，使得密码更加的安全，就算破解了一个，其他的密码也都会非常安全。
    *
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


  /*  */
    /**
     * @author: Liang Shan
     * @date: 2019-11-06
     * @time: 11:52
     * @param: auth
     * @description: 这里需要重写配置类的一个configure方法
     * 使用我们自己的加密方式
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     * // myUserDetailsService之前的自己实现UserDetailService接口的类,然后再调用自己实现的加密方式
     * auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
     * }
     */

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* // 定义认证方式为表单登录
        http.formLogin()
                // 定义对下列都是授权配置
                .and().authorizeRequests()
                // 对所有请求做授权
                .anyRequest()
                // 都需要身份认证
                .authenticated();*/

        // 自定义表单登录页面配置
        http.formLogin()
                // .loginPage("/authentication/require")
                // 放入表单登录路径,这样才会触发usernameAndPasswordFilter
                .loginProcessingUrl("/zidingyibiaodan/form").permitAll()
                .successHandler(myAuthenticationSuccessHandler)
                .and()
                // 表示下面的信息都是配置授权信息
                .authorizeRequests()
                .antMatchers("/authentication/require",
                             securityProperties.getBrowser().getLoginPage())
                .permitAll()
                // 处理跨域请求中的Preflight请求
                .requestMatchers().permitAll()
                // 对所有请求做授权
                .anyRequest()
                // 都需要身份认证
                .authenticated()
                .and()
                // 先暂时关闭csrf
                .csrf().disable()
                // 开启跨域
                .cors();

    }

}
