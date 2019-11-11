package com.ls.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Liang Shan
 * @date: 2019-11-08 14:34
 * @description: 登录失败执行器
 */
@Component("myAuthenticationFailureHandler")
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /*
     * @author: Liang Shan
     * @date: 2019-11-08
     * @time: 14:51
     * @param: httpServletRequest
     * @Param: httpServletResponse
     * @Param: e
     * @description: 登录过程中产生的错误AuthenticationException对象
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("登录失败");
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(e));
    }
}
