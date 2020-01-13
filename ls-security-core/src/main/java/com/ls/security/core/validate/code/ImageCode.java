package com.ls.security.core.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @program: bpczy
 * @description: 图片验证码
 * @author: Liang Shan
 * @created: 2020/01/04 17:46
 */
@Data
public class ImageCode {
    /* 图片展示*/
    private BufferedImage image;
    /* 验证码*/
    private String code;
    /* 过期时间*/
    private LocalDateTime expireTime;

    /* expireIn 是在多少秒内过期*/
    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
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
