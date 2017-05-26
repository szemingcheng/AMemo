package com.szemingcheng.amemo.model;

import com.szemingcheng.amemo.entity.Memo;

/**
 * Created by szemingcheng on 2017/5/23.
 */

public interface MemoDetailModel {
    interface OnDataFinishedListener{
        void getDataFinish(Memo memo);
        void onError(String error);
    }
    interface OnRequestListener{
        void onSuccess();
        void onError(String error);
        void onDeleteSuccess();
        void onRestoreSuccess();
    }
    void get_memo_detail(Long id,OnDataFinishedListener onDataFinishedListener);
    void save_memo(Memo memo, OnRequestListener onRequestListener);
    void update_memo(Memo memo,OnRequestListener onRequestListener);
    void delete_memo(Long memo_id,OnRequestListener onRequestListener);
    void memo_restore(Long memo_id,OnRequestListener onRequestListener);
    void memo_remove(Long memo_id,OnRequestListener onRequestListener);
}
