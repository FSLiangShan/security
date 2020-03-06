package com.ls.security.core.validate.code;

import com.ls.security.core.authentication.mobile.SmsCodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

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
    public static final String SESSION_KEY_PREFIX = "SESSION_KEY_";

    /**
     * @description: spring的依赖搜索，spring会把所有ValidateGenerator实现查找出来
     * @author: Liang Shan
     * @updateTime: 2020/1/20 15:49
     * @throws:
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;
    /* 依赖搜索的前置判断条件*/
    private String getRequestType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }
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
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCodeGenerator imageCodeGenerator = validateCodeGenerators.get("imageCodeGenerator");
        /* 根据随机数生成图片*/
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        /* 将生成的随机数存放到当前session中*/
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        /* 将生成的图片写到接口的响应中*/
        ImageIO.write(imageCode.getImage(), "JPG", response.getOutputStream());
    }

    /**
     * @description: 创建一个短信验证码
     * @author: Liang Shan
     * @updateTime: 2020/1/20 14:06
     * @throws:
     */
    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request) throws IOException, ServletRequestBindingException {
        ValidateCodeGenerator smsGenerator = validateCodeGenerators.get("smsGenerator");
        /* 生成短信验证码并且向短信服务商发送短信验证码
         * ServletWebRequest是spring封装的一个工具类
         * 不仅可以放Request，还可以放Response，不用每次传入两个参数*/
        ValidateCode validateCode = smsGenerator.generate(new ServletWebRequest(request));
        /* 将生成的随机数存放到当前session中*/
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_PREFIX + "SMS", validateCode);
    }
}
