package com.szemingcheng.amemo.utils;

/**
 * Created by Jaygren on 2017/5/24.
 */

public class RegexUtils {
    //用户名验证
    //6~15任意英文字母规则
    //电话手机号码
    public static int UseridRegex(String Userid) {
        String userRegex = "\\w{4,15}";
        String userRegex2 = "1[3|5|8][0-9]\\d{8}";
        if (!Userid.matches(userRegex) && !Userid.matches(userRegex2)) {
            return 0;
        }
        return 1;
    }
    //密码验证
    //6~15任意英文字母规则
    public static int PasswdRegex(String Passwd){
        String passwdRegex = "\\w{6,15}";
        if(!Passwd.matches(passwdRegex)){
            return 0;
        }
        return 1;
    }
    //手机验证
    //6~15任意英文字母规则
    public static int PhoneRegex(String Phone){
        String phoneRegex = "1[3|5|8][0-9]\\d{8}";
        if(!Phone.matches(phoneRegex)){
            return 0;
        }
        return 1;
    }
}