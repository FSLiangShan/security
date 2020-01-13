package com.ls.security.core.validate.code;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @program: bpczy
 * @description: 验证码控制层
 * @author: Clover
 * @created: 2020/01/04 18:05
 */
@RestController
public class ValidateCodeController {

    /* 使用当前session的自定义属性*/
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * @title: creatImageCode
     * @description: 创建一个图片验证码
     * @author: Liang Shan
     * @param: request
     * @param: response
     * @updateTime: 2020/1/4 18:02
     * @return: com.lw.bpczy.common.response.ServerResponse
     * @throws:
     */
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /* 根据随机数生成图片*/
        ImageCode imageCode = createImageCode();
        /* 将生成的随机数存放到当前session中*/
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        /* 将生成的图片写到接口的响应中*/
        ImageIO.write(imageCode.getImage(), "JPG", response.getOutputStream());
    }

    /**
     * @description: 生成图形验证码
     * @author: Liang Shan
     * @updateTime: 2020/1/4 19:38
     * @throws:
     */
    private ImageCode createImageCode() throws IOException {
        // 生成四位数的随机数
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        // 生成图片验证码
        BufferedImage bufferedImage = VerifyCodeUtil.outputImage(300, 100, verifyCode);
        return new ImageCode(bufferedImage, verifyCode, 60);
    }
}
