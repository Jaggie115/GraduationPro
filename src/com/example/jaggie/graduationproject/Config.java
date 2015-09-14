package com.example.jaggie.graduationproject;

import cn.bmob.v3.BmobUser;

/**
 * 预配置类
 * 
 * @author Administrator
 */
public class Config {


    public static final String APP_KEY = "0c90501b7a6cf201222a";
    public static final String APP_SECRET = "47affa27fd6879b6592162a774e6509625cf3ace";
    public static final String ACCOUNT_PRE = "https://api.shanbay.com/account/";
    public static final String WORD_PRE = "https://api.shanbay.com/bdc/search/";
    public static final String REDIRECT_URL = "https://api.shanbay.com/oauth2/auth/success/";
    public static String accessToken;
    public static String refreshToken;
    public static String tokenType;
    public static int expires_in;
    public static String scope;


    // bmob 权限key//
    public static final String APPLICATION_KEY = "152ee7951f2580b41dc0d3ca349aaaa0";


    // Bmob //
    public static BmobUser loginUser;

}
