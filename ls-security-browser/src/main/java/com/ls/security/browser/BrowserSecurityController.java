package com.ls.security.browser;

import com.ls.security.core.SecurityProperties;
import com.ls.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Liang Shan
 * @date: 2019-11-07 10:21
 * @description: 网页安全跳转控制层
 */
@RestController
@Slf4j
public class BrowserSecurityController {


    /** 请求缓存中可以拿到这次请求的信息*/
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();// spring的重定向类
    @Autowired
    private SecurityProperties securityProperties;
    /*
    * @author: Liang Shan
    * @date: 2019-11-07
    * @time: 11:48
    * @param: request
    * @Param: response
    * @description: spring security判断认证不通过时跳转进入此控制器
    */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)// 如果不是html访问则返回401未授权状态码
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // 如果这是html请求，就跳转
            String url = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是：" + url);
            if (StringUtils.endsWithIgnoreCase(url, "html")) {
                // 将用户跳转到自定义的LoginPage上
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }
}
