package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.entity.Memo;

/**
 * Created by szemingcheng on 2017/5/23.
 */

public interface MemoDetailActivityView {

    void initViewOnViewMode(Memo memo);
    void initViewOnCreateMode();
    void showSaveMemoDialog();
    void showSaveMemoSuccess();
    void showSaveMemoFail(String error);
}
