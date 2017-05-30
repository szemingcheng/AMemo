package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.MemoHelper;
import com.szemingcheng.amemo.db.NoteBKHelper;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.MemoDetailModel;
import com.szemingcheng.amemo.utils.HtmlRegexpUtil;

/**
 * Created by szemingcheng on 2017/5/23.
 */

public class MemoDetailModelImp implements MemoDetailModel {
    private MemoHelper memoHelper = DBUtils.getMemoDriverHelper();
    private UserHelper userHelper = DBUtils.getUserDriverHelper();
    private NoteBKHelper noteBKHelper = DBUtils.getNoteBKDriverHelper();

    @Override
    public void get_memo_detail(Long id, OnDataFinishedListener onDataFinishedListener) {
        Memo  memo = memoHelper.query(id);
        onDataFinishedListener.getDataFinish(memo);
    }

    @Override
    public void save_memo(Memo memo, OnRequestListener onRequestListener) {
        boolean error = false;
         if (memo.getNoteBK() == null) {
                onRequestListener.onError("请选择笔记本！");
                error = true;
        }
        if (!error) {
            String data =memo.getContext();
            String is_PIC;
            if (HtmlRegexpUtil.getImgSrcList(data).size()!=0&&HtmlRegexpUtil.getImgSrcList(data).size()>0){
                is_PIC = HtmlRegexpUtil.getImgSrcList(data).get(0);
                memo.setType(Memo.TYPE_CAM);
                memo.setPic(is_PIC);
            }
            else {
                memo.setType(Memo.TYPE_TXT);
            }
            if (memo.getTitle().isEmpty()){
                memo.setTitle("未命名笔记");
            }
            String memo_txt = HtmlRegexpUtil.filterHtml(data);
            memo.setMemotxt(memo_txt);
            memo.setCreatat(System.currentTimeMillis());
            memo.setUpdateat(System.currentTimeMillis());
            memo.setState(Memo.IS_EXSIT);
            if (App.getAppcontext().getUser_ID().equals("")){
                User user = userHelper.queryBuilder().where(UserDao.Properties._ID.eq(1L)).unique();
                memo.setUser(user);
            }
            else {
                User user = userHelper.queryBuilder()
                        .where(UserDao.Properties.User_id.eq(App.getAppcontext().getUser_ID())).unique();
                memo.setUser(user);
            }
            memoHelper.save(memo);
            noteBKHelper.refresh(memo.getNoteBK());
            onRequestListener.onSuccess();
        }
    }

    @Override
    public void update_memo(Memo memo, OnRequestListener onRequestListener) {
        boolean error = false;
        if (memo.getNoteBK() == null) {
            onRequestListener.onError("请选择笔记本！");
            error = true;
        }
        if (!error) {
            String data =memo.getContext();
            String is_PIC;
            if (HtmlRegexpUtil.getImgSrcList(data).size()>0){
                is_PIC = HtmlRegexpUtil.getImgSrcList(data).get(0);
                memo.setType(Memo.TYPE_CAM);
                memo.setPic(is_PIC);
            }
            else {
                memo.setType(Memo.TYPE_TXT);
            }
            if (memo.getTitle().isEmpty()){
                memo.setTitle("未命名笔记");
            }
            String memo_txt = HtmlRegexpUtil.filterHtml(data);
            memo.setMemotxt(memo_txt);
            memo.setUpdateat(System.currentTimeMillis());
            memoHelper.saveOrUpdate(memo);
            onRequestListener.onSuccess();
        }
    }

    @Override
    public void delete_memo(Long memo_id, OnRequestListener onRequestListener) {
        boolean error = false;
        Memo memo = memoHelper.queryBuilder().where(MemoDao.Properties._ID.eq(memo_id)).unique();
        int state = memo.getState();
        if (state == Memo.IS_DELETE||state!=Memo.IS_EXSIT){
            onRequestListener.onError("未知错误，删除失败");
            error = true;
        }
        else {
            memo.setState(Memo.IS_DELETE);
            memo.setNoteBK(null);
            memoHelper.update(memo);
        }
        if (!error){
            onRequestListener.onDeleteSuccess();
        }
    }

    @Override
    public void memo_restore(Long memo_id, OnRequestListener onRequestListener) {
        boolean error = false;
        Memo memo = memoHelper.queryBuilder().where(MemoDao.Properties._ID.eq(memo_id)).unique();
        int state = memo.getState();
        Long User_id = memo.getUser_ID();
        if (state == Memo.IS_EXSIT||state!=Memo.IS_DELETE){
            onRequestListener.onError("未知错误，恢复失败");
            error = true;
        }
        else {
            memo.setState(Memo.IS_EXSIT);
            NoteBK noteBK = userHelper.query(User_id).getNoteBKs().get(0);
            memo.setNoteBK(noteBK);
            memoHelper.update(memo);
        }
        if (!error){
            onRequestListener.onRestoreSuccess();
        }
    }

    @Override
    public void memo_remove(Long memo_id, OnRequestListener onRequestListener) {
        boolean error = false;
        Memo memo = memoHelper.queryBuilder().where(MemoDao.Properties._ID.eq(memo_id)).unique();
        int state = memo.getState();
        if (state == Memo.IS_EXSIT||state!=Memo.IS_DELETE){
            onRequestListener.onError("未知错误，移除失败");
            error = true;
        }
        else{
            memoHelper.delete(memo);
        }
        if (!error){
            onRequestListener.onDeleteSuccess();
        }
    }
}
