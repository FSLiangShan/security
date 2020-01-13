package com.ls.security.core.validate.code;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: bpczy
 * @description: 在校验用户名和密码之前的验证码校验过滤器，
 * 继承Spring提供的filter它能够确保在一次请求中只通过一次filter，而需要重复的执行
 * @author: Clover
 * @created: 2020/01/06 13:02
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    /**
     * @title: doFilterInternal
     * @description: filter具体的业务执行逻辑
     * @author: Liang Shan
     * @param: httpServletRequest
     * @param: httpServletResponse
     * @param: filterChain
     * @updateTime: 2020/1/6 13:37
     * @throws:
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 判断是否是表单登录请求
        if (StringUtils.equals("/zidingyibiaodan/form", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase("post", httpServletRequest.getMethod())) {
            // 如果是表单登录就尝试做验证码校验
            try {
                // 这里做校验，先将request包装一下
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                // 捕获到异常后，使用失败处理器
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * @description: 校验验证码
     * @author: Liang Shan
     * @updateTime: 2020/1/6 14:12
     * @throws:
     */
    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ImageCode codeOInSession = (ImageCode)sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
        // 获取request中的验证码值
        String imageCodeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
        if (codeOInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (StringUtils.isBlank(imageCodeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (codeOInSession.isExpired()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期，请刷新后重试");
        }
        if (!StringUtils.equalsIgnoreCase(codeOInSession.getCode(), imageCodeInRequest)) {
            throw new ValidateCodeException("验证码错误");
        }
        // 全部正确以后然后移除
        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
    }
}
