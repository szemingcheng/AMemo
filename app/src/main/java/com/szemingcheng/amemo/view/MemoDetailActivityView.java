package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.NoteBK;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/23.
 */

public interface MemoDetailActivityView {
    void setToolbarTitle(String title);
    void initViewOnViewMode(Memo memo);
    void initViewOnCreateMode();
    void initViewSelectNoteBK(List<NoteBK> noteBKs);
    void setDoneMenuItemVisible(boolean visible);
    boolean isDoneMenuItemVisible();
    void showSaveMemoDialog();
    void showSaveMemoSuccess();
    void showSaveMemoFail();
}
