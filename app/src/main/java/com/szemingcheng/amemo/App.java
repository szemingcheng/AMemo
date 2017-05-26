package com.szemingcheng.amemo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.szemingcheng.amemo.manager.DaoManger;
import com.szemingcheng.amemo.manager.UserManager;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class App extends Application {
        private static App Appcontext;
        private static String UserId;
    @Override
    public void onCreate() {
        super.onCreate();

//        测试代码
//        PreferencesUtils.putUserid(this,PreferencesUtils.USERID, String.valueOf(System.currentTimeMillis()),PreferencesUtils.PHONE,"1813805986",PreferencesUtils.ONSCREENNAME, String.valueOf(System.currentTimeMillis()));
//        PreferencesUtils.logined(this,PreferencesUtils.LOGINED,true);
//        PreferencesUtils.putBoolean(this,PreferencesUtils.FIRST_START,true);

        //sqlite可视化工具
        Stetho.initializeWithDefaults(this);
        //初始化Context
        Appcontext= this;

        //UserDao全局判断用户在线状态
        UserId= UserManager.getUserId();

        //greenDao全局配置,只获取一个数据库操作对象
        //调用greenDao会话层，获取Dao类操作方法
         DaoManger.getDaoSession(UserId);

    }

    //获取Context
    public static App getAppcontext() {
        return Appcontext;
    }


   public static String getUserId() {
        return UserId;
    }


}
