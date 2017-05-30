package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.App;
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
            noteBKs = noteBKHelper.queryBuilder().where(NoteBKDao.Properties.User_id.eq(_id))
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
        User user = userHelper.queryBuilder().where(UserDao.Properties.User_id.eq(App.getAppcontext().getUser_ID())).unique();
        Long _Id = user.get_ID();
        List<NoteBK>noteBKs=noteBKHelper.queryBuilder().where(NoteBKDao.Properties.User_id.eq(_Id)).list();
        if (memos.size()>0){
            onRequestListener.onError("该笔记本不为空");
        }else if(noteBKs.size()<=1){
            onRequestListener.onError("该笔记为最后一本笔记，恳请您不要删除" +
                    "\n(ಥ _ ಥ)");
        }
        else {
            noteBKHelper.deleteByKey(_id);
            onRequestListener.onSuccess();
        }
    }

    @Override
    public void notebk_add(NoteBK noteBK, OnRequestListener onRequestListener) {
        boolean error = false;
        String userid = App.getAppcontext().getUser_ID();
        if (userid.equals("")){
            User user = userHelper.queryBuilder()
                    .where(UserDao.Properties._ID.eq(1L))
                    .unique();
            List<NoteBK> notes = noteBKHelper.queryBuilder()
                    .where(NoteBKDao.Properties.User_id.eq(1L)).list();
            for (NoteBK noteBK1 : notes) {
                if (noteBK1.getTitle().equals(noteBK.getTitle())) {
                    error = true;
                    onRequestListener.onError("笔记本标题不能重复！");
                }
            }
            if (!error){
                noteBK.setNotebk_id(String.valueOf(System.currentTimeMillis()));
                noteBK.setUser(user);
                noteBKHelper.save(noteBK);
                onRequestListener.onSuccess(noteBK);
            }
        }
        else {
            User user = userHelper.queryBuilder()
                    .where(UserDao.Properties.User_id.eq(App.getAppcontext().getUser_ID()))
                    .unique();
            Long user_id = user.get_ID();
            List<NoteBK> notes = noteBKHelper.queryBuilder()
                    .where(NoteBKDao.Properties.User_id.eq(user_id)).list();
            for (NoteBK noteBK1:notes){
                if (noteBK1.getTitle().equals(noteBK.getTitle())) {
                    error = true;
                    onRequestListener.onError("笔记本标题不能重复！");
                }
            }
            if (!error){
                noteBK.setNotebk_id(String.valueOf(System.currentTimeMillis()));
                noteBK.setUser(user);
                noteBKHelper.save(noteBK);
                onRequestListener.onSuccess(noteBK);
            }
        }

    }

    @Override
    public void notebk_save(NoteBK noteBK, OnRequestListener onRequestListener) {
        boolean error = false;
        String userid = App.getAppcontext().getUser_ID();
        if (userid.equals("")){
            User user = userHelper.queryBuilder()
                    .where(UserDao.Properties._ID.eq(1L))
                    .unique();
            List<NoteBK> notes = noteBKHelper.queryBuilder()
                    .where(NoteBKDao.Properties.User_id.eq(1L)).list();
            for (NoteBK noteBK1 : notes) {
                if (noteBK1.getTitle().equals(noteBK.getTitle())) {
                    error = true;
                    onRequestListener.onError("笔记本标题不能重复！");
                }
            }
            if (!error){
                noteBKHelper.update(noteBK);
                onRequestListener.onSuccess();
            }
        }
        else {
            User user = userHelper.queryBuilder()
                    .where(UserDao.Properties.User_id.eq(App.getAppcontext().getUser_ID()))
                    .unique();
            Long user_id = user.get_ID();
            List<NoteBK> notes = noteBKHelper.queryBuilder()
                    .where(NoteBKDao.Properties.User_id.eq(user_id)).list();
            for (NoteBK noteBK1:notes){
                if (noteBK1.getTitle().equals(noteBK.getTitle())) {
                    error = true;
                    onRequestListener.onError("笔记本标题不能重复！");
                }
            }
            if (!error){
                noteBKHelper.update(noteBK);
                onRequestListener.onSuccess();
            }
        }
    }
}
