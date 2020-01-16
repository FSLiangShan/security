package com.ls.security.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Liang Shan
 * @date: 2019-11-05 16:17
 * @description: 实现安全框架用户登录
 */
@Component("userDetailService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
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
        /*将授权的字符串变为授权对象集合，
        AuthorityUtils的commaSeparatedStringToAuthorityList可以将逗号分隔的字符串变为授权集合，比如角色，就是ROLE_ADMIN类似于这种*/
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
        // todo 根据用户信息判断是否合法
        // 使用Security自带的用户对象，已经实现了UserDetails接口,写到用户名，密码，和权限
        // 这个 passwordEncoder.encode("123456")动作是每次在注册的时候做的，因为是采用了 BCryptPasswordEncoder的加密方式，所以每一次encode()的值都是不同的，即使是一样的明文密码
        String encode = passwordEncoder.encode("123456");
        User user = new User("admin",encode, true, true, true, true, grantedAuthorityList);
        log.info("加密后的密码："+encode);
        return user;
    }

}

