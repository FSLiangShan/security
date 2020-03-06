package com.ls.security.core.validate.code;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;

/**
 * @program: security
 * @description: 图片验证码生成器
 * @author: Clover
 * @created: 2020/01/20 10:17
 */
@Component("imageCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {

    /**
     * @description: 生成图形验证码
     * @author: Liang Shan
     * @updateTime: 2020/1/4 19:38
     * @throws:
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {
        // 生成四位数的随机数
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        // 生成图片验证码
        BufferedImage bufferedImage = VerifyCodeUtil.outputImage(300, 100, verifyCode);
        return new ImageCode(bufferedImage, verifyCode, 60);
    }
}
