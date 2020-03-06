package com.ls.security.core.authentication.mobile;

import lombok.Data;

/**
 * @program: security
 * @description: 发送短信验证码返回实体类
 * @author: Clover
 * @created: 2020/01/23 22:03
 */
@Data
public class SendSmsCodeResult {
    private String code;
    private String msg;
    private String obj;
}
