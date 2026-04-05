package com.RouteMate.framework.web.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.RouteMate.common.constant.Constants;
import com.RouteMate.common.core.domain.entity.SysUser;
import com.RouteMate.common.core.domain.model.LoginUser;
import com.RouteMate.common.exception.ServiceException;
import com.RouteMate.common.utils.DateUtils;
import com.RouteMate.common.utils.MessageUtils;
import com.RouteMate.common.utils.StringUtils;
import com.RouteMate.common.utils.ip.IpUtils;
import com.RouteMate.framework.manager.AsyncManager;
import com.RouteMate.framework.manager.factory.AsyncFactory;
import com.RouteMate.framework.wechat.WechatApiUtil;
import com.RouteMate.framework.wechat.WechatApiUtil.WechatSessionInfo;
import com.RouteMate.system.service.ISysUserService;

/**
 * 微信登录校验方法
 *
 * @author RouteMate
 */
@Component
public class WechatLoginService
{
    private static final Logger log = LoggerFactory.getLogger(WechatLoginService.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WechatApiUtil wechatApiUtil;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    /** 微信用户默认密码 */
    private static final String DEFAULT_WECHAT_PASSWORD = "123456";

    /**
     * 微信登录
     *
     * @param code 微信登录code
     * @param nickName 用户昵称
     * @param avatar 用户头像
     * @return token
     */
    public String wechatLogin(String code, String nickName, String avatar)
    {
        try
        {
            // 1. 调用微信接口获取openid和session_key
            WechatSessionInfo sessionInfo = wechatApiUtil.code2Session(code);
            String openid = sessionInfo.getOpenid();

            if (StringUtils.isEmpty(openid))
            {
                throw new ServiceException("获取微信用户信息失败");
            }

            log.info("微信登录，openid: {}", openid);

            // 2. 根据openid查询用户是否存在
            SysUser user = userService.selectUserByWechatOpenid(openid);

            if (user == null)
            {
                // 3. 用户不存在，创建新用户
                user = createWechatUser(sessionInfo, nickName, avatar);
                userService.insertUser(user);
                log.info("创建微信用户成功，userId: {}, openid: {}", user.getUserId(), openid);
            }

            // 4. 检查用户状态
            validateUserStatus(user);

            // 5. 记录登录信息
            recordLoginInfo(user.getUserId());

            // 6. 生成token
            LoginUser loginUser = new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
            String token = tokenService.createToken(loginUser);

            // 7. 记录登录日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

            return token;
        }
        catch (Exception e)
        {
            log.error("微信登录失败", e);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor("微信用户", Constants.LOGIN_FAIL, e.getMessage()));
            throw new ServiceException("微信登录失败: " + e.getMessage());
        }
    }

    /**
     * 创建微信用户
     */
    private SysUser createWechatUser(WechatSessionInfo sessionInfo, String nickName, String avatar)
    {
        SysUser user = new SysUser();
        user.setUserName("wx_" + sessionInfo.getOpenid().substring(0, 8)); // 用openid前8位作为用户名
        user.setNickName(StringUtils.isNotEmpty(nickName) ? nickName : "微信用户");
        user.setAvatar(StringUtils.isNotEmpty(avatar) ? avatar : "");

        // 设置默认密码为123456（加密）
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(DEFAULT_WECHAT_PASSWORD));

        user.setWechatOpenid(sessionInfo.getOpenid());
        user.setWechatUnionid(sessionInfo.getUnionid());
        user.setWechatSessionKey(sessionInfo.getSessionKey());
        user.setLoginType("wechat");
        user.setStatus("0"); // 正常状态
        user.setDelFlag("0"); // 未删除
        user.setSex("2"); // 未知
        user.setDeptId(103L); // 默认部门，可根据需要调整
        user.setPwdUpdateDate(DateUtils.getNowDate()); // 设置密码更新时间
        user.setCreateBy("system");
        user.setCreateTime(DateUtils.getNowDate());
        user.setRemark("微信登录用户，默认密码：" + DEFAULT_WECHAT_PASSWORD);

        return user;
    }

    /**
     * 更新微信用户信息
     */
    private void updateWechatUser(SysUser user, WechatSessionInfo sessionInfo, String nickName, String avatar)
    {
        // 更新会话信息
        user.setWechatSessionKey(sessionInfo.getSessionKey());
        if (StringUtils.isNotEmpty(sessionInfo.getUnionid()))
        {
            user.setWechatUnionid(sessionInfo.getUnionid());
        }

        // 更新用户信息（如果传入了新的信息）
        if (StringUtils.isNotEmpty(nickName))
        {
            user.setNickName(nickName);
        }
        if (StringUtils.isNotEmpty(avatar))
        {
            user.setAvatar(avatar);
        }

        user.setUpdateBy("system");
        user.setUpdateTime(DateUtils.getNowDate());
    }

    /**
     * 校验用户状态
     */
    private void validateUserStatus(SysUser user)
    {
        if ("2".equals(user.getDelFlag()))
        {
            throw new ServiceException("用户已被删除");
        }
        if ("1".equals(user.getStatus()))
        {
            throw new ServiceException("用户已被停用");
        }
    }

    /**
     * 记录登录信息
     */
    private void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
