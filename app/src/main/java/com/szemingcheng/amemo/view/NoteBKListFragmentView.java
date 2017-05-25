package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.entity.NoteBK;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/20.
 */

public interface NoteBKListFragmentView {
    void updateListView(List<NoteBK> noteBKs);
    void showLoadingIcon();
    void hideLoadingIcon();
    void showRecyclerView();
    void hideRecyclerView();
    void showError(String error);
    void AddSuccess(NoteBK noteBK);
    void showSuccess();
}
