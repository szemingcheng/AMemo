package com.szemingcheng.amemo;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;
import com.szemingcheng.amemo.dao.DaoSession;
import com.szemingcheng.amemo.manager.DaoManger;
import com.szemingcheng.amemo.manager.UserManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class App extends Application {
        private static App Appcontext;
        private static DaoSession mDaoSession;
        private static Long Visitor_id;
        private static String User_ID;
        public static List<Activity> activityList = new LinkedList<Activity>();


    @Override
    public void onCreate() {
        super.onCreate();
        //sqlite可视化工具
        Stetho.initializeWithDefaults(this);
        //初始化Context
        Appcontext= this;

        Visitor_id = 1L;


        //UserDao全局判断用户在线状态
        User_ID= UserManager.getUserId();

        //greenDao全局配置,只获取一个数据库操作对象
        //调用greenDao会话层，获取Dao类操作方法
        mDaoSession=DaoManger.getDaoSession(User_ID);

    }

    public static App getAppcontext() {
        return Appcontext;
    }

    public  Long getVisitor_id() {
        return Visitor_id;
    }

    public  String getUser_ID() {
        return User_ID;
    }

    public  void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }
    public static DaoSession getDaoSession() {
        return mDaoSession;
    }


    // 遍历所有Activity finish
    public void delete()
    {
        for (Activity activity : activityList)
        {
            activity.finish();
        }
        if (activityList.size() == 0)
            activityList.clear();
    }
}
