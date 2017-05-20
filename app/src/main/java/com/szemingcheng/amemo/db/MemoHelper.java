package com.szemingcheng.amemo.db;

import com.szemingcheng.amemo.entity.Memo;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public class MemoHelper extends BaseDBHelper<Memo,Long> {
    public MemoHelper(AbstractDao dao) {
        super(dao);
    }
}
