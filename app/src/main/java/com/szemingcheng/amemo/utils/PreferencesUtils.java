package com.szemingcheng.amemo.utils;

/**
 * Created by Jaygren on 2017/5/22.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.szemingcheng.amemo.entity.User;

/**
* @date on 2017/5/22
* @author Jaygren
* @title  PreferencesUtils
* @describe 关于记录系统的默认偏好设置
*
*/


public class PreferencesUtils {
    public static String PREFERENCE_NAME = "AMemo";
    public static String FIRST_START = "firstStart";
    public static String LOGINED="logined";
    public static String USERID="Userid";
    public static String PHONE="Phone";
    public static String ONSCREENNAME="Onscreen_name";
    public static String PASSWORD="password";
    public static User user;

    /**
     * 数据库首操作判断
     * @param context .APP(全程序可控Application)
     * @param key     preference应用关键字
     * @param defaultValue  默认关键字 true
     * @return boolean
     */

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 数据库首操作关闭
     * @param context .APP(全应用可控Application)
     * @param key     preference应用关键字
     * @param value   应用关键字 false
     * @return boolean
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 登录状态判断
     * @param context .APP(全应用可控Application)
     * @param key     preference应用关键字
     * @param defaultValue 默认关键字
     * @return    boolean
     */
    public static boolean islogin(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 登录状态控制
     * @param context .APP(全应用可控Application)
     * @param key      preference应用关键字
     * @param value   应用关键字

     * @return boolean
     */
    public static boolean logined(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    /**
     *  FALSE 为 unlogin
     *  TRUE 为 login
     */

    /**
     *
     * @param context .APP(全应用可控Application)
     * @param key  preference应用关键字
     * @param defaultValue 默认关键字
     * @return String
     */
    public static String getUserId(Context context,String key,String defaultValue ){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     *
     * @param context
     * @param key1     User_id
     * @param value1
     * @param key2     Phone
     * @param value2
     * @param key3     Onscreen_name
     * @param value3
     * @param key4     Password
     * @param value4
     * @return
     */
    public static boolean putUserid(Context context, String key1, String value1,String key2,String value2,String key3,String value3,String key4,String value4){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key1, value1);
        editor.putString(key2, value2);
        editor.putString(key3, value3);
        editor.putString(key4,value4);
        return editor.commit();
    }

    /**
     *
     * @param context
     * @param key1  User_id
     * @param key2  Phone
     * @param key3  Onscreen_name
     * @param key4  password
     * @return User
     */
    public static User getUserInfo(Context context, String key1, String key2, String key3, String key4 ){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        user=new User();
        user.set_ID(null);
        user.setUser_id((settings.getString(key1,null)));
        user.setPhone(settings.getString(key2,null));
        user.setOnscreen_name(settings.getString(key3,null));
        user.setPasswrd(settings.getString(key4,null));
        return  user;
    }




    /**
     *
     * @param context .APP(全应用可控Application)
     */
    public static void clear(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

}
