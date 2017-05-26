package com.szemingcheng.amemo.presenter;

/**
 * Created by szemingcheng on 2017/5/25.
 */

public interface TrashListFragmentPresent {
    void getMemo(String user_id);
    void pulltorefresh(String user_id);
    void memo_restore(Long memo_id);
    void memo_delete(Long memo_id);
}
