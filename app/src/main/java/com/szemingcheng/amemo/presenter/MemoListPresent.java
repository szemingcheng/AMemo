package com.szemingcheng.amemo.presenter;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public interface MemoListPresent {
    void getMemo(String user_id);
    void pulltorefresh(String user_id);
    void getMemo_notebk(String note_title);
    void pulltorefresh_notebk(String note_title);
    void delete_memo(Long memo_id);
}
