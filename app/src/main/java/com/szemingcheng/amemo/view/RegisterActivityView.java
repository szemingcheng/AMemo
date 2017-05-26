package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.ui.login.activity.RegisterActivity;

/**
 * Created by Jaygren on 2017/5/26.
 */

public interface RegisterActivityView {
    String getUserid();
    String getPassword1();
    String getPassword2();
    String getPhone();
    void UseridFocus();
    void Password1Focus();
    void Password2Focus();
    void PhoneFocus();
    void UseridMatchRegex();
    void PasswdMatchRegex();
    void PhoneMatchRegex();
    void UserInfoClare();
    void PasswordCompared();
    RegisterActivity getContext();
}
