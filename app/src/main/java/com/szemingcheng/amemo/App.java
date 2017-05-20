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
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Appcontext= this;
//        DaoSession daoSession = getDaoSession();
//            UserDao userDao = daoSession.getUserDao();
//        User user = userDao.queryBuilder().where(UserDao.Properties._ID.eq(1L)).unique();
//        user.setType(User.TYPE_VISITOR);
//        userDao.insertOrReplace(user);
//            NoteBKDao noteBKDao = daoSession.getNoteBKDao();
//            MemoDao memoDao = daoSession.getMemoDao();
//        User user = new User();
//        userDao.insert(user);
//        NoteBK noteBK = new NoteBK();
//        noteBK.setTitle("我的第一个笔记本");
//        noteBKDao.insert(noteBK);
//        for(int i = 0;i<=11;i++){
//            Memo memo = new Memo();
//            memo.setTitle("听说今天5.20。标题"+i);
//            memo.setMemotxt("关我什么事"+getString(R.string.test));
//            memo.setUser(user);
//            memo.setState(Memo.IS_EXSIT);
//            memo.setType(Memo.TYPE_TXT);
//            memo.setNoteBK(noteBK);
//            memo.setCreatat((System.currentTimeMillis() - 86400000 * i -31536000000L*i ));
//            memo.setUpdateat((System.currentTimeMillis() - 86400000 * i-31536000000L*i+ 3600000 * i));
//            memoDao.insert(memo);
//        }
//            for (int i = 0; i <= 3; i++) {
//                Note note = new Note();
//                note.setTitle("test" + i);
//                note.setMemotxt(getString(R.string.test));
//                note.setUser(user);
//                note.setState(Note.IS_EXSIT);
//                note.setCreatat((System.currentTimeMillis() - 86400000 * i));
//                note.setUpdateat((System.currentTimeMillis() - 86400000 * i + 3600000 * i));
//                noteDao.insert(note);
//            }
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

    private void initDaoSession() {
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        DaoMaster.DevOpenHelper helper =new DaoMaster.DevOpenHelper(this,"notetest-db",null);
        // 得到可写的数据库操作对象
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
    }
}
