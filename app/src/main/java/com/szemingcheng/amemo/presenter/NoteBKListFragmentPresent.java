package com.szemingcheng.amemo.presenter;

import com.szemingcheng.amemo.entity.NoteBK;

/**
 * Created by szemingcheng on 2017/5/20.
 */

public interface NoteBKListFragmentPresent {
    void getMemo(String user_id);
    void pulltorefresh(String user_id);
    void add_NoteBK(NoteBK noteBK);
    void delete_NoteBK(Long notebk_id);
    void update_NoteBK(NoteBK noteBK);
}
