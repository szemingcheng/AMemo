package com.szemingcheng.amemo.model;

import com.szemingcheng.amemo.entity.NoteBK;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/18.
 */

public interface NoteBKListModel {
    interface OnDataFinishedListener{
        void noDataFirstLoad(String error);
        void getDataFinish(List<NoteBK> noteBKs);
        void onError(String error);
    }
    interface OnRequestListener{
        void onSuccess();
        void onError(String error);
    }
    void notebk_list(String userid, OnDataFinishedListener onDataFinishedListener);
}
