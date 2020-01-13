package com.ls.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @program: bpczy
 * @description: 验证码校验异常
 * @author: Clover
 * @created: 2020/01/06 13:54
 */
public class ValidateCodeException extends AuthenticationException
{

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
