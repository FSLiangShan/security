package com.ls.security.core;

import lombok.Data;

/**
 * @author: Liang Shan
 * @date: 2019-11-07 13:35
 * @description: 系统配置网页配置
 */
@Data
public class BrowserProperties {
    // 在这里声明一下默认的登录页
    private String loginPage = "/ls-signIn.html";
}
