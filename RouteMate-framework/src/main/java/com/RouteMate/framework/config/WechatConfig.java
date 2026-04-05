package com.RouteMate.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置
 * 
 * @author RouteMate
 */
@Component
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatConfig
{
    /** 小程序AppID */
    private String appId;

    /** 小程序AppSecret */
    private String appSecret;

    /** 微信API基础URL */
    private String apiBaseUrl = "https://api.weixin.qq.com";

    /** code2Session接口URL */
    private String code2SessionUrl = "/sns/jscode2session";

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppSecret()
    {
        return appSecret;
    }

    public void setAppSecret(String appSecret)
    {
        this.appSecret = appSecret;
    }

    public String getApiBaseUrl()
    {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl)
    {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getCode2SessionUrl()
    {
        return code2SessionUrl;
    }

    public void setCode2SessionUrl(String code2SessionUrl)
    {
        this.code2SessionUrl = code2SessionUrl;
    }

    /**
     * 获取完整的code2Session URL
     */
    public String getFullCode2SessionUrl()
    {
        return apiBaseUrl + code2SessionUrl;
    }
}
