package com.RouteMate.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.RouteMate.common.constant.Constants;
import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.utils.StringUtils;
import com.RouteMate.framework.web.service.WechatLoginService;

/**
 * 微信登录控制器
 *
 * @author RouteMate
 */
@RestController
@RequestMapping("/wechat")
public class WechatLoginController extends BaseController
{
    @Autowired
    private WechatLoginService wechatLoginService;

    /**
     * 微信小程序登录
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody WechatLoginBody loginBody)
    {
        // 参数校验
        if (StringUtils.isEmpty(loginBody.getCode()))
        {
            return error("登录code不能为空");
        }

        try
        {
            // 微信登录
            String token = wechatLoginService.wechatLogin(
                loginBody.getCode(),
                loginBody.getNickName(),
                loginBody.getAvatar()
            );

            AjaxResult ajax = AjaxResult.success();
            ajax.put(Constants.TOKEN, token);
            return ajax;
        }
        catch (Exception e)
        {
            return error("微信登录失败: " + e.getMessage());
        }
    }

    /**
     * 微信登录请求体
     */
    public static class WechatLoginBody
    {
        /** 微信登录code */
        private String code;

        /** 用户昵称 */
        private String nickName;

        /** 用户头像 */
        private String avatar;

        /** 用户性别 */
        private String gender;

        /** 用户所在国家 */
        private String country;

        /** 用户所在省份 */
        private String province;

        /** 用户所在城市 */
        private String city;

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public String getNickName()
        {
            return nickName;
        }

        public void setNickName(String nickName)
        {
            this.nickName = nickName;
        }

        public String getAvatar()
        {
            return avatar;
        }

        public void setAvatar(String avatar)
        {
            this.avatar = avatar;
        }

        public String getGender()
        {
            return gender;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
        }

        public String getCountry()
        {
            return country;
        }

        public void setCountry(String country)
        {
            this.country = country;
        }

        public String getProvince()
        {
            return province;
        }

        public void setProvince(String province)
        {
            this.province = province;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        @Override
        public String toString()
        {
            return "WechatLoginBody{" +
                "code='" + code + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
        }
    }
}
