package com.szemingcheng.amemo.manager;

import android.database.sqlite.SQLiteDatabase;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.dao.DaoMaster;
import com.szemingcheng.amemo.dao.DaoSession;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.utils.PreferencesUtils;

/**
 * Created by Jaygren on 2017/5/21.
 */

/**
* @author Jaygren
* @title  DaoManger
* @describe 唯一数据库控制
*
*/


public class DaoManger {
    private static DaoSession mDaoSession;
    public static User user;
    private static SQLiteDatabase db ;


    //线程锁，强制主进程优先进行数据库的初始化，保证异步操作安全
    public static synchronized DaoSession getDaoSession(String Userid) {
        if (mDaoSession == null) {
            initDaoSession(Userid);
        }
        return mDaoSession;
    }

    private static void initDaoSession(String Userid) {
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        //建立数据库
        DaoMaster.DevOpenHelper helper =new DaoMaster.DevOpenHelper(App.getAppcontext(),"notetest-db",null);
        // 得到可写的数据库操作对象
        db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
        //用户偏向，sharedPreference创建默认游客相关信息
        //判断是否为游客状态
        if(PreferencesUtils.getUserId(App.getAppcontext(),PreferencesUtils.USERID,"").equals("")) {
            if (PreferencesUtils.getBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, true)) {
                //创建默认游客user
                mDaoSession.getUserDao().insert(new User(1L, Userid, null, null, Userid, null, User.TYPE_VISITOR));
                //创建默认游客notebook
                mDaoSession.getNoteBKDao().insert(new NoteBK(1l, "第一本笔记本", "第一本笔记本", 1l));

                //PreferencesUtils.putUserid(App.getAppcontext(),PreferencesUtils.USERID,null,PreferencesUtils.PHONE,null,PreferencesUtils.ONSCREENNAME,null,PreferencesUtils.PASSWORD,null);
                //插入测试数据
                //创建user
//                UserDao userDao = mDaoSession.getUserDao();
//                User user = userDao.queryBuilder().where(UserDao.Properties._ID.eq(1L)).unique();

                //创建notebook
//                NoteBKDao noteBKDao = mDaoSession.getNoteBKDao();
//                NoteBK noteBK = noteBKDao.queryBuilder().where(NoteBKDao.Properties._ID.eq(1L)).unique();

//                MemoDao memoDao = mDaoSession.getMemoDao();
//                for (int i = 0; i <= 2; i++) {
//                    Memo memo = new Memo();
//                    memo.setTitle("小本本标题" + i);
//                    memo.setMemotxt("好的这是文字item");
//                    memo.setUser(user);
//                    memo.setState(Memo.IS_EXSIT);
//                    memo.setType(Memo.TYPE_TXT);
//                    memo.setNoteBK(noteBK);
//                    memo.setCreatat((System.currentTimeMillis() - 86400000 * i - 31536000000L * i));
//                    memo.setUpdateat((System.currentTimeMillis() - 86400000 * i - 31536000000L * i + 3600000 * i));
//                    memoDao.insert(memo);
//                }
//                for (int i = 0; i <= 2; i++) {
//                    Memo memo = new Memo();
//                    memo.setTitle("小本本标题" + i);
//                    memo.setMemotxt("好的这是图片item");
//                    memo.setUser(user);
//                    memo.setState(Memo.IS_EXSIT);
//                    memo.setType(Memo.TYPE_CAM);
//                    memo.setPic(String.valueOf(R.drawable.user3));
//                    memo.setNoteBK(noteBK);
//                    memo.setCreatat((System.currentTimeMillis() - 86400000 * i - 31536000000L * i));
//                    memo.setUpdateat((System.currentTimeMillis() - 86400000 * i - 31536000000L * i + 3600000 * i));
//                    memoDao.insert(memo);
//                }
//                for (int i = 0; i <= 2; i++) {
//                    Memo memo = new Memo();
//                    memo.setTitle("小本本标题" + i);
//                    memo.setMemotxt("好的这是提醒item");
//                    memo.setUser(user);
//                    memo.setState(Memo.IS_EXSIT);
//                    memo.setType(Memo.TYPE_REMINDER);
//                    memo.setNoteBK(noteBK);
//                    memo.setReminder_date(System.currentTimeMillis() + 3600000 * 2);
//                    memo.setCreatat((System.currentTimeMillis() - 86400000 * i - 31536000000L * i));
//                    memo.setUpdateat((System.currentTimeMillis() - 86400000 * i - 31536000000L * i + 3600000 * i));
//                    memoDao.insert(memo);
//                }
            }
            //偏向识别关闭
            //将settings的FIRST_START关键字记录为false
            PreferencesUtils.putBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, false);
        }else{
            if (PreferencesUtils.getBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, true)) {
                user = PreferencesUtils.getUserInfo(App.getAppcontext(), PreferencesUtils.USERID, PreferencesUtils.PHONE, PreferencesUtils.ONSCREENNAME,PreferencesUtils.PASSWORD);
                //创建默认用户user
                mDaoSession.getUserDao().insert(new User(null,user.getUser_id(), null, user.getPhone(), user.getOnscreen_name(), null, User.TYPE_USER));
                UserDao userDao = mDaoSession.getUserDao();
                User userInfo = userDao.queryBuilder().where(UserDao.Properties.User_id.eq(user.getUser_id())).unique();
                //创建默认用户notebook
                NoteBKDao noteBKDao = mDaoSession.getNoteBKDao();
                NoteBK noteBK=new NoteBK();
                noteBK.set_ID(null);
                noteBK.setUser(userInfo);
                noteBK.setNotebk_id(String.valueOf(System.currentTimeMillis()));
                noteBK.setTitle("第一本笔记本");
                noteBKDao.insert(noteBK);
                PreferencesUtils.logined(App.getAppcontext(),PreferencesUtils.LOGINED,false);
            }
            PreferencesUtils.putBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, false);
        }
}
//    private static void initUserDb(String Userid) {
//        getDaoSession(Userid);
//    }

}
