package com.szemingcheng.amemo.db;

import com.szemingcheng.amemo.entity.User;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public class UserHelper extends BaseDBHelper<User,Long> {
    public UserHelper(AbstractDao dao) {
        super(dao);
    }
}
