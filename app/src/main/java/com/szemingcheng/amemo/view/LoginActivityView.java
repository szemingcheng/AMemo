package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.ui.login.activity.LoginActivity;

/**
 * Created by Jaygren on 2017/5/24.
 */

public interface LoginActivityView {
    String getUserid();
    String getPassword();
    void setUserInfo(String userid,String password);
    void clear();
    void UseridRequestFocus();
    void PasswordRequestFocus();
    void UseridUnMatchRegex();
    void PasswordUnMatchRegex();
    void CheckUserid();
    LoginActivity getContext();

}
