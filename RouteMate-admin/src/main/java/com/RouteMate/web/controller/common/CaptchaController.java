package com.RouteMate.web.controller.common;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.ChineseGifCaptcha;
import com.wf.captcha.base.Captcha;
import com.RouteMate.common.config.NaiweilanlanConfig;
import com.RouteMate.common.constant.CacheConstants;
import com.RouteMate.common.constant.Constants;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.core.redis.RedisCache;
import com.RouteMate.common.utils.uuid.IdUtils;
import com.RouteMate.system.service.ISysConfigService;

/**
 * 验证码操作处理
 *
 * @author Naiweilanlan
 */
@RestController
public class CaptchaController
{
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysConfigService configService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException
    {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String code = null;
        String image = null;

        // 根据配置生成不同类型的验证码
        String captchaType = NaiweilanlanConfig.getCaptchaType();
        Captcha captcha = null;

        try {
            if ("math".equals(captchaType))
            {
                // 算术类型验证码，自动生成算术表达式
                ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(160, 60);
                arithmeticCaptcha.setLen(2);  // 几位数运算，默认是两位
                arithmeticCaptcha.getArithmeticString();  // 获取运算的公式：3+2=?
                code = arithmeticCaptcha.text();  // 获取运算的结果：5
                captcha = arithmeticCaptcha;
            }
            else if ("char".equals(captchaType))
            {
                // 字符验证码
                captcha = new SpecCaptcha(160, 60, 4);
                code = captcha.text();
            }
            else if ("gif".equals(captchaType))
            {
                // gif类型验证码
                captcha = new GifCaptcha(160, 60, 4);
                code = captcha.text();
            }
            else if ("chinese".equals(captchaType))
            {
                // 中文验证码 - 必须使用系统中文字体
                captcha = new ChineseCaptcha(160, 60, 4);
                code = captcha.text();
            }
            else if ("chinese_gif".equals(captchaType))
            {
                // 中文gif验证码 - 必须使用系统中文字体
                captcha = new ChineseGifCaptcha(160, 60, 4);
                code = captcha.text();
            }
            else
            {
                // 默认使用算术验证码
                ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(160, 60);
                arithmeticCaptcha.setLen(2);
                arithmeticCaptcha.getArithmeticString();
                code = arithmeticCaptcha.text();
                captcha = arithmeticCaptcha;
            }

            // 设置验证码字体
            setFont(captcha, captchaType);

            // 获取base64编码的图片
            image = captcha.toBase64();

        } catch (Exception e) {
            return AjaxResult.error("生成验证码失败：" + e.getMessage());
        }

        // 存入redis并设置过期时间
        redisCache.setCacheObject(verifyKey, code.toLowerCase(), Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        ajax.put("uuid", uuid);
        ajax.put("img", image);
        return ajax;
    }

    /**
     * 设置验证码字体
     *
     * @param captcha 验证码对象
     * @param captchaType 验证码类型
     */
    private void setFont(Captcha captcha, String captchaType) throws IOException, FontFormatException {
        NaiweilanlanConfig.CaptchaFontConfig fontConfig = NaiweilanlanConfig.getCaptchaFont();

        // 如果配置为空，使用默认值
        if (fontConfig == null) {
            fontConfig = new NaiweilanlanConfig.CaptchaFontConfig();
        }

        // 中文验证码使用系统字体
        if ("chinese".equals(captchaType) || "chinese_gif".equals(captchaType)) {
            String fontName = fontConfig.getChineseFont();
            Integer fontSize = fontConfig.getChineseFontSize();
            Integer fontStyle = fontConfig.getChineseFontStyle();

            captcha.setFont(new Font(fontName, fontStyle, fontSize));
        } else {
            // 非中文验证码使用内置字体
            Integer builtinFont = fontConfig.getBuiltinFont();

            // 根据配置的数字设置对应的内置字体
            switch (builtinFont) {
                case 1: captcha.setFont(Captcha.FONT_1); break;
                case 2: captcha.setFont(Captcha.FONT_2); break;
                case 3: captcha.setFont(Captcha.FONT_3); break;
                case 4: captcha.setFont(Captcha.FONT_4); break;
                case 5: captcha.setFont(Captcha.FONT_5); break;
                case 6: captcha.setFont(Captcha.FONT_6); break;
                case 7: captcha.setFont(Captcha.FONT_7); break;
                case 8: captcha.setFont(Captcha.FONT_8); break;
                case 9: captcha.setFont(Captcha.FONT_9); break;
                case 10: captcha.setFont(Captcha.FONT_10); break;
                default: captcha.setFont(Captcha.FONT_9); break;
            }
        }
    }
}
