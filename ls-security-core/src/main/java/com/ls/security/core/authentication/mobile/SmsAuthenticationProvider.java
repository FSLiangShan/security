package com.ls.security.core.authentication.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @program: security
 * @description: 短信登录校验provider
 * @author: Clover
 * @created: 2020/01/17 11:41
 */
@Data
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
    * @title: 组装认证成功后用户信息, 认证成功前进入的方法
    * @author: Liang Shan
    * @updateTime: 2020/1/17 14:24
    * @throws:
    */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 认证成功前的authentication是token
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        // 拿出来的是手机号
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) smsAuthenticationToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        // 如果读到了用户信息，就重新构造结构
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        // 再把request详细信息设置到新的result结果中
        authenticationResult.setDetails(smsAuthenticationToken.getDetails());
        return authenticationResult;
    }

    /**
    * @description: 是manager用来判断哪个provider具体执行的
    * @author: Liang Shan
    * @updateTime: 2020/1/17 13:37
    * @throws:
    */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}