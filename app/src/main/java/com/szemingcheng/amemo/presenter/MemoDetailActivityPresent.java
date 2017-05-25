package com.szemingcheng.amemo.presenter;

import com.szemingcheng.amemo.entity.Memo;

/**
 * Created by szemingcheng on 2017/5/23.
 */

public interface MemoDetailActivityPresent {

    void load_memo_detail(Long memo_id);
    void save_memo_detail(Memo memo);
    void update_memo_detail(Memo memo);

}
