package com.ls.security.core.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @program: security
 * @description: 校验码
 * @author: Clover
 * @created: 2020/01/17 15:23
 */
@Data
public class ValidateCode {

    /* 校验码*/
    private String code;
    /* 过期时间*/
    private LocalDateTime expireTime;

    /* expireIn 是在多少秒内过期*/
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode() {

    }


    /* 验证是否过期*/
    public boolean isExpired() {
        if (this.expireTime.isBefore(LocalDateTime.now())) {
            return true;
        } else {
            return false;
        }
    }
}
