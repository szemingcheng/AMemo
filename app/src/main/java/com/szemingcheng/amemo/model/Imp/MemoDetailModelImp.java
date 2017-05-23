package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.MemoHelper;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.MemoDetailModel;
import com.szemingcheng.amemo.utils.HtmlRegexpUtil;

/**
 * Created by szemingcheng on 2017/5/23.
 */

public class MemoDetailModelImp implements MemoDetailModel {
    private MemoHelper memoHelper = DBUtils.getMemoDriverHelper();
    private UserHelper userHelper = DBUtils.getUserDriverHelper();
    @Override
    public void get_memo_detail(Memo memo, OnDataFinishedListener onDataFinishedListener) {
        onDataFinishedListener.getDataFinish(memo);
    }

    @Override
    public void save_memo(Memo memo, OnRequestListener onRequestListener) {
        boolean error = false;
        if (memo.getTitle().isEmpty() && memo.getMemotxt().isEmpty()) {
                onRequestListener.onError("不能保存空笔记哟~");
                error = true;
        } else if (memo.getNoteBK() == null) {
                onRequestListener.onError("请选择笔记本！");
                error = true;
        }
        if (!error) {
            String data =memo.getContext();
            String is_PIC = HtmlRegexpUtil.getImgSrcList(data).get(0);
            if (is_PIC==null){
                memo.setType(Memo.TYPE_TXT);
            }
            else {
                memo.setType(Memo.TYPE_CAM);
                memo.setPic(is_PIC);
            }
            String memo_txt = HtmlRegexpUtil.filterHtml(data);
            memo.setMemotxt(memo_txt);
            memo.setCreatat(System.currentTimeMillis());
            memo.setUpdateat(System.currentTimeMillis());
            if (App.getAppcontext().getUser_ID()==null){
                User user = userHelper.queryBuilder().where(UserDao.Properties._ID.eq(1L)).unique();
                memo.setUser(user);
            }
            else {
                User user = userHelper.queryBuilder()
                        .where(UserDao.Properties._ID.eq(App.getAppcontext().getUser_ID())).unique();
                memo.setUser(user);
            }
            memoHelper.save(memo);
            onRequestListener.onSuccess();
        }
    }

    @Override
    public void update_memo(Memo memo, OnRequestListener onRequestListener) {
        boolean error = false;
        if (memo.getTitle().isEmpty() && memo.getMemotxt().isEmpty()) {
            onRequestListener.onError("错误！");
            error = true;
        } else if (memo.getNoteBK() == null) {
            onRequestListener.onError("请选择笔记本！");
            error = true;
        }
        if (!error) {
            String data =memo.getContext();
            String is_PIC = HtmlRegexpUtil.getImgSrcList(data).get(0);
            if (is_PIC==null){
                memo.setType(Memo.TYPE_TXT);
            }
            else {
                memo.setType(Memo.TYPE_CAM);
                memo.setPic(is_PIC);
            }
            String memo_txt = HtmlRegexpUtil.filterHtml(data);
            memo.setMemotxt(memo_txt);
            memo.setUpdateat(System.currentTimeMillis());
            memoHelper.saveOrUpdate(memo);
            onRequestListener.onSuccess();
        }
    }
}
