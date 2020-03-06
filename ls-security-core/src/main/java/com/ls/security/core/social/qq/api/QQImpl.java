package com.ls.security.core.social.qq.api;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @program: security
 * @description: 实现QQ接口
 * @author: Clover
 * @created: 2020/01/30 14:17
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    // 获取openid的请求地址，有需要的参数accessToken
    private static String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    // 获取用户信息的请求地址，QQ api中的路径还有accessToken，已经删除，父类会帮我们挂上，我们不需要处理
    private static String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    // qq互联注册的应用id
    private String appId;
    // qq用户的唯一标识
    private String openId;

    /* 构造函数要放两个参数，accessToken和appId都需要外部来传值*/
    public QQImpl(String accessToken, String appId) {
        /* 调用父类的构造方法，第二个参数是设置请求url中token传值的参数策略
        父类默认情况下（一个参数的构造函数） accessToken是放在请求头中的，
        但是QQ文档上写的AccessToken是作为参数放在url中的，所以需要改变策略。
        */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        // 获取openId，调用父类提供的请求方法
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
    }
    @Override
    public QQUserInfo getUserInfo() {
        return null;
    }
}
