package com.ls.security.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: Liang Shan
 * @date: 2019-11-07 13:36
 * @description: 系统配置总配置
 */
@Data
@ConfigurationProperties(prefix = "ls.security") // 只要是ls.security的
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();
}
