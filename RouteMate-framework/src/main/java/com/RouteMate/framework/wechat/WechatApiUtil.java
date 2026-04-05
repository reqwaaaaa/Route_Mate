package com.RouteMate.framework.wechat;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.RouteMate.common.exception.ServiceException;
import com.RouteMate.framework.config.WechatConfig;

/**
 * 微信API工具类
 * 
 * @author RouteMate
 */
@Component
public class WechatApiUtil
{
    private static final Logger log = LoggerFactory.getLogger(WechatApiUtil.class);

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 微信小程序登录凭证校验
     * 
     * @param code 登录时获取的 code
     * @return 包含openid、session_key等信息的对象
     */
    public WechatSessionInfo code2Session(String code)
    {
        try
        {
            // 构建请求参数
            Map<String, String> params = new HashMap<>();
            params.put("appid", wechatConfig.getAppId());
            params.put("secret", wechatConfig.getAppSecret());
            params.put("js_code", code);
            params.put("grant_type", "authorization_code");

            // 构建请求URL
            String url = wechatConfig.getFullCode2SessionUrl() + 
                "?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";

            log.info("调用微信code2Session接口，code: {}", code);

            // 发送请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
            String responseBody = response.getBody();

            log.info("微信code2Session接口响应: {}", responseBody);

            // 解析响应
            JSONObject jsonObject = JSON.parseObject(responseBody);
            
            // 检查是否有错误
            if (jsonObject.containsKey("errcode"))
            {
                Integer errcode = jsonObject.getInteger("errcode");
                String errmsg = jsonObject.getString("errmsg");
                log.error("微信code2Session接口调用失败，errcode: {}, errmsg: {}", errcode, errmsg);
                throw new ServiceException("微信登录失败: " + errmsg);
            }

            // 构建返回对象
            WechatSessionInfo sessionInfo = new WechatSessionInfo();
            sessionInfo.setOpenid(jsonObject.getString("openid"));
            sessionInfo.setSessionKey(jsonObject.getString("session_key"));
            sessionInfo.setUnionid(jsonObject.getString("unionid"));

            return sessionInfo;
        }
        catch (Exception e)
        {
            log.error("调用微信code2Session接口异常", e);
            throw new ServiceException("微信登录失败: " + e.getMessage());
        }
    }

    /**
     * 微信会话信息
     */
    public static class WechatSessionInfo
    {
        /** 用户唯一标识 */
        private String openid;

        /** 会话密钥 */
        private String sessionKey;

        /** 用户在开放平台的唯一标识符 */
        private String unionid;

        public String getOpenid()
        {
            return openid;
        }

        public void setOpenid(String openid)
        {
            this.openid = openid;
        }

        public String getSessionKey()
        {
            return sessionKey;
        }

        public void setSessionKey(String sessionKey)
        {
            this.sessionKey = sessionKey;
        }

        public String getUnionid()
        {
            return unionid;
        }

        public void setUnionid(String unionid)
        {
            this.unionid = unionid;
        }

        @Override
        public String toString()
        {
            return "WechatSessionInfo{" +
                "openid='" + openid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
        }
    }
}
