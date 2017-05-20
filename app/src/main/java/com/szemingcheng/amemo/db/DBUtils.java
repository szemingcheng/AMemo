package com.szemingcheng.amemo.db;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public class DBUtils {
    private static UserHelper userHelper;
    private static NoteBKHelper noteBKHelper;
    private static MemoHelper memoHelper;

    private static UserDao getUserDriverDao() {
        return App.getAppcontext().getDaoSession().getUserDao();
    }
    private static NoteBKDao getNoteBKDriverDao() {
        return App.getAppcontext().getDaoSession().getNoteBKDao();
    }
    private static MemoDao getMemoDriverDao() {
        return App.getAppcontext().getDaoSession().getMemoDao();
    }
    public static UserHelper getUserDriverHelper() {
        if (userHelper == null) {
            userHelper = new UserHelper(getUserDriverDao());
        }
        return userHelper;
    }
    public static NoteBKHelper getNoteBKDriverHelper() {
        if (noteBKHelper == null) {
            noteBKHelper = new NoteBKHelper(getNoteBKDriverDao());
        }
        return noteBKHelper;
    }
    public static MemoHelper getMemoDriverHelper() {
        if (memoHelper == null) {
            memoHelper = new MemoHelper(getMemoDriverDao());
        }
        return memoHelper;
    }
}
