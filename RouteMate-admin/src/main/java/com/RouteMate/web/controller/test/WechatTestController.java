package com.RouteMate.web.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.core.domain.model.LoginBody;
import com.RouteMate.framework.config.WechatConfig;
import com.RouteMate.framework.wechat.WechatApiUtil;

/**
 * 微信功能测试控制器
 * 
 * @author RouteMate
 */
@RestController
@RequestMapping("/test/wechat")
public class WechatTestController extends BaseController
{
    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WechatApiUtil wechatApiUtil;

    /**
     * 测试微信配置
     */
    @GetMapping("/config")
    public AjaxResult testConfig()
    {
        AjaxResult result = AjaxResult.success();
        result.put("appId", wechatConfig.getAppId());
        result.put("apiBaseUrl", wechatConfig.getApiBaseUrl());
        result.put("code2SessionUrl", wechatConfig.getFullCode2SessionUrl());
        return result;
    }

    /**
     * 测试微信code2Session接口
     * 注意：这个接口仅用于测试，实际使用时应该通过小程序获取真实的code
     */
    @GetMapping("/code2session")
    public AjaxResult testCode2Session(@RequestParam String code)
    {
        try
        {
            WechatApiUtil.WechatSessionInfo sessionInfo = wechatApiUtil.code2Session(code);
            return AjaxResult.success(sessionInfo);
        }
        catch (Exception e)
        {
            return AjaxResult.error("测试失败: " + e.getMessage());
        }
    }

    /**
     * 测试登录数据接收
     */
    @PostMapping("/login-data")
    public AjaxResult testLoginData(@RequestBody LoginBody loginBody)
    {
        AjaxResult result = AjaxResult.success();
        result.put("message", "成功接收登录数据");
        result.put("receivedData", loginBody);
        result.put("username", loginBody.getUsername());
        result.put("usernameType", loginBody.getUsername() != null ? loginBody.getUsername().getClass().getSimpleName() : "null");
        return result;
    }

    /**
     * 获取测试说明
     */
    @GetMapping("/help")
    public AjaxResult help()
    {
        AjaxResult result = AjaxResult.success();
        result.put("message", "微信登录测试说明");
        result.put("steps", new String[]{
            "1. 确保数据库已执行微信登录相关的SQL更新脚本",
            "2. 检查application.yml中的微信配置是否正确",
            "3. 访问 /test/wechat/config 检查配置是否加载成功",
            "4. 在小程序中调用wx.login()获取code",
            "5. 使用获取到的code调用 /wechat/login 接口进行登录测试",
            "6. 检查数据库sys_user表中是否创建了微信用户记录"
        });
        result.put("apis", new String[]{
            "GET /test/wechat/config - 查看微信配置",
            "GET /test/wechat/code2session?code=xxx - 测试code2Session接口",
            "POST /wechat/login - 微信登录接口",
            "POST /test/wechat/login-data - 测试登录数据接收",
            "GET /test/wechat/help - 查看帮助信息"
        });
        return result;
    }
}
