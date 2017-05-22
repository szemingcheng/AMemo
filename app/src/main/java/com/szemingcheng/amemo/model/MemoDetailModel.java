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
    }
    void get_memo_detail(Memo memo,OnDataFinishedListener onDataFinishedListener);
    void save_memo(Memo memo, OnRequestListener onRequestListener);
}
