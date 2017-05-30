package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.SettingDeatilModel;

/**
 * Created by Jaygren on 2017/5/29.
 */

public class SettingDeatilModelImp implements SettingDeatilModel {
    private UserHelper userHelper = DBUtils.getUserDriverHelper();
    @Override
    public void user_onscreenname_update(User user) {
        userHelper.update(user);
    }

}
