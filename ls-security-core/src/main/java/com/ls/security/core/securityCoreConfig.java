package com.ls.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Liang Shan
 * @date: 2019-11-07 15:06
 * @description: 使SecurityProperties配置器生效
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class securityCoreConfig {
}
