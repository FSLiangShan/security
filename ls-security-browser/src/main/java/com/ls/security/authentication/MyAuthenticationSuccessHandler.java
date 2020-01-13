package com.ls.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Liang Shan
 * @date: 2019-11-08 10:03
 * @description: 登录成功执行器
 */
@Component(value = "myAuthenticationSuccessHandler")
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /*在spring mvc 启动的时候，会为我们自动注册一个序列化对象objectMapper */
    /*
     * @author: Liang Shan
     * @date: 2019-11-08
     * @time: 10:06
     * @param: httpServletRequest
     * @Param: httpServletResponse
     * @Param: authentication
     * @description: Authentication的实例对象包装着非常多的信息,包括认证请求的信息，比如请求的IP是多少，session是什么，以及认证通过后，
     * Userdetails实例也是包装在Auhtentacation
     * Auhtentacation对象与登录方式的不同
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        ObjectMapper objectMapper = new ObjectMapper();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}
