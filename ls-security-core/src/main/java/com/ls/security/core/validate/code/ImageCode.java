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
public class ImageCode extends ValidateCode{
    /* 图片展示*/
    private BufferedImage image;


    /* expireIn 是在多少秒内过期*/
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
    }

}
