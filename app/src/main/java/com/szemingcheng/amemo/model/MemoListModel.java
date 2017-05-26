package com.szemingcheng.amemo.model;

import com.szemingcheng.amemo.entity.Memo;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public interface MemoListModel {
    interface OnDataFinishedListener{
        void getDataFinish(List<Memo> memos);
        void onError(String error);
    }
    interface OnRequestListener{
        void onSuccess();
        void onError(String error);
    }
    void memo_all(String userid,OnDataFinishedListener onDataFinishedListener);
    void memo_in_notebk(String notebk_title,OnDataFinishedListener onDataFinishedListener);
    void memo_in_trash(String userid,OnDataFinishedListener onDataFinishedListener);
    void memo_delete(Long memo_id,OnRequestListener onRequestListener);
    void memo_in_type(String userid,int type,OnDataFinishedListener onDataFinishedListener);
    void memo_restore(Long memo_id,OnRequestListener onRequestListener);
    void memo_remove(Long memo_id,OnRequestListener onRequestListener);
}
