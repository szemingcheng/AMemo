package com.szemingcheng.amemo.view;

import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.szemingcheng.amemo.ui.unlogin.activity.setting.SettingDeatilActivity;

/**
 * Created by Jaygren on 2017/5/29.
 */

public interface SettingDeatilActivityView {
    String getOnScreenName();

    String getPasswd();

    String getPhone();

    String getPhoneNumber();

    Long getID();

    void initViewOnScreenName();

    void initViewPasswd();

    void initViewCam(Menu menu);

    void initViewPhone();

    SettingDeatilActivity getContext();

    Toolbar getToolbar();

    void OnScreamClare();

    void PasswdClare();

    void PhoneClare();
}
