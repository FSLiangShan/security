package com.ls.security.core.authentication.mobile;

import com.ls.security.core.validate.code.ValidateCode;
import com.ls.security.core.validate.code.ValidateCodeException;
import com.ls.security.core.validate.code.ValidateCodeGenerator;
import com.ls.security.core.validate.code.VerifyCodeUtil;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @program: security
 * @description: 短信验证码生成器
 * @author: Clover
 * @created: 2020/01/17 16:57
 */
@Component("smsGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
    //发送验证码的请求路径URL
    private static final String
            SERVER_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的AppKey
    private static final String
            APP_KEY="86ff61e628020c78205f4f8414543218";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="5d0387764a1e";
    //短信模板ID
    private static final String TEMPLATE_ID="14845845";
    // 语音模板ID
    // private static final String TEMPLATE_ID = "14842857";
    //验证码长度，范围4～10，默认为4
    private static final String CODE_LEN="4";

    /**
     * @description: 生成短信验证码
     * @author: Liang Shan
     * @updateTime: 2020/1/20 10:52
     * @throws:
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) throws ServletRequestBindingException, IOException {
        // 取出手机号
        String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), "mobile");
        // 生成四位随机数
        String nonce = VerifyCodeUtil.generateVerifyNumber(Integer.parseInt(CODE_LEN));
        System.out.println(nonce);
        SendSmsCodeResult sendSmsCodeResult = new SendSmsCodeResult();
        if (!sendCode(sendSmsCodeResult, mobile, nonce)) {
            throw new ValidateCodeException("发送验证码请求失败");
        }
        // 设置过期时间五分钟
        System.out.println("获取到结果:"+sendSmsCodeResult);
        return new ValidateCode(sendSmsCodeResult.getObj(), 60 * 5);
    }

    private Boolean sendCode(SendSmsCodeResult sendSmsCodeResult,String mobile,String nonce) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        // 当前时间
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, nonce, curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvpList = new ArrayList<>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
        nvpList.add(new BasicNameValuePair("templateid", TEMPLATE_ID));
        nvpList.add(new BasicNameValuePair("mobile", mobile));
        nvpList.add(new BasicNameValuePair("codeLen", CODE_LEN));
        httpPost.setEntity(new UrlEncodedFormEntity(nvpList, "utf-8"));
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = JSONObject.fromObject(result);
        sendSmsCodeResult = (SendSmsCodeResult)JSONObject.toBean(jsonObject, SendSmsCodeResult.class);
        System.out.println("转换之后的结果:" + sendSmsCodeResult);
        return StringUtils.equals(sendSmsCodeResult.getCode(), "200");
    }
}
