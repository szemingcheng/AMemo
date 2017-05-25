package com.szemingcheng.amemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.stetho.Stetho;
import com.szemingcheng.amemo.dao.DaoMaster;
import com.szemingcheng.amemo.dao.DaoSession;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class App extends Application {
        private static App Appcontext;
        private static DaoSession mDaoSession;
        private static Long Visitor_id;
        private static String User_ID = "";
        private static SQLiteDatabase db ;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Appcontext= this;
//        DaoSession daoSession = getDaoSession();
//            UserDao userDao = daoSession.getUserDao();
//        User user = new User();
//        user.setType(User.TYPE_VISITOR);
//        user.set_ID(1L);
////        User user = userDao.queryBuilder().where(UserDao.Properties._ID.eq(1L)).unique();
//        NoteBKDao noteBKDao = daoSession.getNoteBKDao();
////        NoteBK noteBK = noteBKDao.queryBuilder().where(NoteBKDao.Properties._ID.eq(1L)).unique();
//        NoteBK noteBK = new NoteBK();
//        noteBK.setUser(user);
//        noteBK.set_ID(1L);
//        noteBK.setTitle("我的第一个笔记本");
//        noteBKDao.insert(noteBK);
//        userDao.insert(user);
//            MemoDao memoDao = daoSession.getMemoDao();
//
//        for(int i = 0;i<=2;i++){
//            Memo memo = new Memo();
//            memo.setTitle("小本本标题"+i);
//            memo.setMemotxt("好的这是文字item");
//            memo.setUser(user);
//            memo.setState(Memo.IS_EXSIT);
//            memo.setType(Memo.TYPE_TXT);
//            memo.setNoteBK(noteBK);
//            memo.setCreatat((System.currentTimeMillis() - 86400000 * i -31536000000L*i ));
//            memo.setUpdateat((System.currentTimeMillis() - 86400000 * i-31536000000L*i+ 3600000 * i));
//            memoDao.insert(memo);
//        }
//        for(int i = 0;i<=2;i++){
//            Memo memo = new Memo();
//            memo.setTitle("小本本标题"+i);
//            memo.setMemotxt("好的这是图片item");
//            memo.setUser(user);
//            memo.setState(Memo.IS_EXSIT);
//            memo.setType(Memo.TYPE_CAM);
//            memo.setPic(String.valueOf(R.drawable.user3));
//            memo.setNoteBK(noteBK);
//            memo.setCreatat((System.currentTimeMillis() - 86400000 * i -31536000000L*i ));
//            memo.setUpdateat((System.currentTimeMillis() - 86400000 * i-31536000000L*i+ 3600000 * i));
//            memoDao.insert(memo);
//        }
//        for(int i = 0;i<=2;i++){
//            Memo memo = new Memo();
//            memo.setTitle("小本本标题"+i);
//            memo.setMemotxt("好的这是提醒item");
//            memo.setUser(user);
//            memo.setState(Memo.IS_EXSIT);
//            memo.setType(Memo.TYPE_REMINDER);
//            memo.setNoteBK(noteBK);
//            memo.setReminder_date(System.currentTimeMillis()+3600000*2);
//            memo.setCreatat((System.currentTimeMillis() - 86400000 * i -31536000000L*i ));
//            memo.setUpdateat((System.currentTimeMillis() - 86400000 * i-31536000000L*i+ 3600000 * i));
//            memoDao.insert(memo);
//        }
        Visitor_id = 1L;
    }

    public static App getAppcontext() {
        return Appcontext;
    }
    public synchronized DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDaoSession();
        }
        return mDaoSession;
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

    private void initDaoSession() {
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        DaoMaster.DevOpenHelper helper =new DaoMaster.DevOpenHelper(this,"notetest-db",null);
        // 得到可写的数据库操作对象
        db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
