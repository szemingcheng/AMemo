package com.szemingcheng.amemo.model;

import com.szemingcheng.amemo.entity.NoteBK;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/18.
 */

public interface NoteBKListModel {
    interface OnDataFinishedListener{
        void getDataFinish(List<NoteBK> noteBKs);
        void onError(String error);
    }
    interface OnRequestListener{
        void onSuccess();
        void onRenameSuccess();
        void onSuccess(NoteBK noteBK);
        void onError(String error);
    }
    void notebk_list(String userid, OnDataFinishedListener onDataFinishedListener);
    void notebk_delete(Long _id, OnRequestListener onRequestListener);
    void notebk_add(NoteBK noteBK, OnRequestListener onRequestListener);
    void notebk_save(NoteBK noteBK1,NoteBK noteBK,OnRequestListener onRequestListener);
}
