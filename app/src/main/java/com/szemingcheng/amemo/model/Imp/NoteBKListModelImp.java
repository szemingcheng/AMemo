package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.NoteBKHelper;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.NoteBKListModel;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/18.
 */

public class NoteBKListModelImp implements NoteBKListModel {
    private NoteBKHelper noteBKHelper = DBUtils.getNoteBKDriverHelper();
    private UserHelper userHelper = DBUtils.getUserDriverHelper();
        List<NoteBK> noteBKs;
    @Override
    public void notebk_list(String userid, OnDataFinishedListener onDataFinishedListener) {
        if (userid.equals("")){
            noteBKs = noteBKHelper.queryBuilder().where(NoteBKDao.Properties.User_id.eq(1L))
                    .orderDesc(NoteBKDao.Properties._ID).list();
            onDataFinishedListener.getDataFinish(noteBKs);
        }
        else if (!userid.isEmpty()&&!userid.equals("")){
            User user = userHelper.queryBuilder().where(UserDao.Properties.User_id.eq(userid)).unique();
            Long _id = user.get_ID();
            noteBKs = noteBKHelper.queryBuilder().where(MemoDao.Properties.User_ID.eq(_id))
                    .orderDesc(NoteBKDao.Properties._ID).list();
            onDataFinishedListener.getDataFinish(noteBKs);
        }
        else {
            onDataFinishedListener.onError("error!");
        }
    }

    @Override
    public void notebk_delete(Long _id, OnRequestListener onRequestListener) {
        List<Memo> memos = noteBKHelper.query(_id).getMemos();
        if (memos.size()>0){
            onRequestListener.onError("该笔记本不为空");
        }
        else {
            noteBKHelper.deleteByKey(_id);
            onRequestListener.onSuccess();
        }
    }
}
