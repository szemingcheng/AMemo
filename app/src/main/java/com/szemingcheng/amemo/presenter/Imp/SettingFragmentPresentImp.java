package com.szemingcheng.amemo.presenter.Imp;

import android.content.Intent;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.presenter.SettingFragmentPresent;
import com.szemingcheng.amemo.ui.login.activity.LoginActivity;
import com.szemingcheng.amemo.utils.PreferencesUtils;
import com.szemingcheng.amemo.view.SettingFragmentView;

/**
 * Created by Jaygren on 2017/5/29.
 */

public class SettingFragmentPresentImp implements SettingFragmentPresent {
    SettingFragmentView settingFragmentView;

    public SettingFragmentPresentImp(SettingFragmentView settingFragmentView){
        this.settingFragmentView=settingFragmentView;
    }

    @Override
    public void _exit() {
        PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, false);
        App.getAppcontext().setUser_ID("");
        settingFragmentView.getactivity().startActivity(new Intent(settingFragmentView.getactivity(), LoginActivity.class));
        settingFragmentView.getactivity().finish();
    }

    @Override
    public void _delete() {
        App.getAppcontext().delete();
    }
}
