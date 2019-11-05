package com.ls.security.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Liang Shan
 * @date: 2019-11-05 16:17
 * @description: 安全框架用户登录
 */
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    /*
     * @author: Liang Shan
     * @date: 2019-11-05
     * @time: 16:29
     * @param: s
     * @description: 加载用户登录
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户名:" + username);
        // todo 根据用户名查找用户信息，查找自己的持久层即可
        // 将授权的字符串变为授权对象集合
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        // 使用Security自带的用户对象，已经实现了UserDetails接口,写到用户名，密码，和权限
        User user = new User("admin", "123456", grantedAuthorityList);
        return user;
    }

}

