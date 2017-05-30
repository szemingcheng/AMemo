package com.szemingcheng.amemo.presenter;

/**
 * Created by Jaygren on 2017/5/29.
 */

public interface SettingDeatilActivityPresent {
    boolean matchUseridRegex(String onstreenname);

    boolean matchPasswdRegex(String passwd);

    boolean matchPhonseRegex(String phone);

    void getJson(String step);

    void initViewOnScreenName();

    void initViewPasswd();

    void initViewPhone();

    void initViewCam();

    void parseJSON(String jsonDate);
}
