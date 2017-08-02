package com.szemingcheng.amemo.model;

import com.szemingcheng.amemo.entity.SearchResult;

import java.util.List;

/**
 * Created by szemingcheng on 2017/8/2 15:02.
 */

public interface SearchListModel {
    interface OnDatFinishedListener{
        void getDataFinish(List<SearchResult> searchResults);
        void onError(String error);
    }
    void select_data(String keyword,OnDatFinishedListener onDatFinishedListener);
}
