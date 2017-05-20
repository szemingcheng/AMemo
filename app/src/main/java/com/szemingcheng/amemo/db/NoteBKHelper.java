package com.szemingcheng.amemo.db;

import com.szemingcheng.amemo.entity.NoteBK;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public class NoteBKHelper extends BaseDBHelper<NoteBK,Long> {
    public NoteBKHelper(AbstractDao dao) {
        super(dao);
    }
}
