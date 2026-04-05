package com.RouteMate.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author Naiweilanlan
 */
@Component
@ConfigurationProperties(prefix = "naiweilanlan")
public class NaiweilanlanConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    /** 验证码类型 */
    private static String captchaType;

    /** 验证码字体配置 */
    private static CaptchaFontConfig captchaFont;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        NaiweilanlanConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        NaiweilanlanConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        NaiweilanlanConfig.captchaType = captchaType;
    }

    public static CaptchaFontConfig getCaptchaFont() {
        return captchaFont;
    }

    public void setCaptchaFont(CaptchaFontConfig captchaFont) {
        NaiweilanlanConfig.captchaFont = captchaFont;
    }

    /**
     * 验证码字体配置类
     */
    public static class CaptchaFontConfig {
        /** 内置字体编号 1-10 */
        private Integer builtinFont = 9;
        
        /** 中文字体名称 */
        private String chineseFont = "楷体";
        
        /** 中文字体大小 */
        private Integer chineseFontSize = 32;
        
        /** 中文字体样式 0=PLAIN 1=BOLD 2=ITALIC 3=BOLD+ITALIC */
        private Integer chineseFontStyle = 0;

        public Integer getBuiltinFont() {
            return builtinFont;
        }

        public void setBuiltinFont(Integer builtinFont) {
            this.builtinFont = builtinFont;
        }

        public String getChineseFont() {
            return chineseFont;
        }

        public void setChineseFont(String chineseFont) {
            this.chineseFont = chineseFont;
        }

        public Integer getChineseFontSize() {
            return chineseFontSize;
        }

        public void setChineseFontSize(Integer chineseFontSize) {
            this.chineseFontSize = chineseFontSize;
        }

        public Integer getChineseFontStyle() {
            return chineseFontStyle;
        }

        public void setChineseFontStyle(Integer chineseFontStyle) {
            this.chineseFontStyle = chineseFontStyle;
        }
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
