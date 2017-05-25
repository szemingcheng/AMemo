package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.MemoHelper;
import com.szemingcheng.amemo.db.NoteBKHelper;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.MemoListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public class MemoListModelImp implements MemoListModel {
    private MemoHelper memoHelper = DBUtils.getMemoDriverHelper();
    private UserHelper userHelper = DBUtils.getUserDriverHelper();
    private NoteBKHelper noteBKHelper = DBUtils.getNoteBKDriverHelper();
    private List<Memo> memos = new ArrayList<>();
    @Override
    public void memo_all(String userid,OnDataFinishedListener onDataFinishedListener) {
        if (userid.equals("")){
            memos=memoHelper.queryBuilder().where(MemoDao.Properties.User_ID.eq(1L), MemoDao.Properties.State.eq(Memo.IS_EXSIT))
                    .orderDesc(MemoDao.Properties.Updateat).list();
            onDataFinishedListener.getDataFinish(memos);
        }
        else if (!userid.isEmpty()&&!userid.equals("")){
            User user = userHelper.queryBuilder().where(UserDao.Properties.User_id.eq(userid)).unique();
            Long _id = user.get_ID();
            memos = memoHelper.queryBuilder().where(MemoDao.Properties.User_ID.eq(_id),
                    MemoDao.Properties.State.eq(MemoDao.Properties.State.eq(Memo.IS_EXSIT))).orderDesc(MemoDao.Properties.Updateat).list();
            onDataFinishedListener.getDataFinish(memos);
        }
        else {
            onDataFinishedListener.onError("error!");
        }

    }

    @Override
    public void memo_in_notebk(String notebk_title, OnDataFinishedListener onDataFinishedListener) {
        if (notebk_title.equals("")) {
            onDataFinishedListener.onError("error!");
        }
        else if (!notebk_title.equals("")&&!notebk_title.isEmpty()){
          NoteBK noteBK = noteBKHelper.queryBuilder().where(NoteBKDao.Properties.Title.eq(notebk_title)).unique();
            Long _id=noteBK.get_ID();
            memos = memoHelper.queryBuilder().where(MemoDao.Properties.NoteBK_ID.eq(_id),
                    MemoDao.Properties.State.eq(Memo.IS_EXSIT)).orderDesc(MemoDao.Properties.Updateat).list();
            onDataFinishedListener.getDataFinish(memos);
        }
        else {
            onDataFinishedListener.onError("error!");
        }

    }

    @Override
    public void memo_in_trash(String userid, OnDataFinishedListener onDataFinishedListener) {
        if (userid.equals("")){
            memos= memoHelper.queryBuilder().where(MemoDao.Properties.User_ID.eq(1L),
                    MemoDao.Properties.State.eq(Memo.IS_DELETE)).orderDesc(MemoDao.Properties.Updateat).list();
            onDataFinishedListener.getDataFinish(memos);
        }
        else if (!userid.equals("")){
            User user = userHelper.queryBuilder().where(UserDao.Properties.User_id.eq(userid)).unique();
            Long _id = user.get_ID();
            memos=memoHelper.queryBuilder().where((MemoDao.Properties.User_ID.eq(_id)),
                    MemoDao.Properties.State.eq(Memo.IS_DELETE)).orderDesc(MemoDao.Properties.Updateat).list();
            onDataFinishedListener.getDataFinish(memos);
        }
        else {
            onDataFinishedListener.onError("error!");
        }
    }

    @Override
    public void memo_delete(Long memo_id, OnRequestListener onRequestListener) {
        boolean error = false;
        Memo memo = memoHelper.queryBuilder().where(MemoDao.Properties._ID.eq(memo_id)).unique();
        int state = memo.getState();
        if (state == Memo.IS_DELETE||state!=Memo.IS_EXSIT){
            onRequestListener.onError("未知错误，删除失败");
            error = true;
        }
        else {
            memo.setState(Memo.IS_DELETE);
            memoHelper.update(memo);
        }
        if (!error){
            onRequestListener.onSuccess();
        }

    }

    @Override
    public void memo_in_type(String userid, int type, OnDataFinishedListener onDataFinishedListener) {

    }
}
